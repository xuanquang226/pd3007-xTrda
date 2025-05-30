package services.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import data.dao.AccountDao;
import data.dao.CustomerDao;
import data.dao.RoleAccountDao;
import data.dao.RoleDao;
import data.dto.AccountDTO;
import data.dto.CartDTO;
import data.dto.CustomerDTO;
import data.dto.RoleDTO;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import redis.clients.jedis.exceptions.JedisDataException;
import security.CustomUserDetailService;
import security.JWTProvider;
import services.AccountService;
import services.CartService;
import services.DeviceService;
import services.MailService;
import utils.RedisUtils;
import utils.TupleToken;
import utils.objects.AccessTokenAndIdAccount;
import utils.objects.InfoRefreshToken;

@EnableAsync
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RoleAccountDao roleAccountDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MailService mailService;

    @Override
    public void createAccount(AccountDTO accountDTO, CustomerDTO customerDTO) {
        // create account, customer and cart
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = accountDTO.getPassword();
        String passwordEncode = passwordEncoder.encode(password);
        accountDTO.setPassword(passwordEncode);

        accountDao.createAccount(accountDTO);
        AccountDTO account = accountDao.getOneAccountByUserName(accountDTO.getUserName());
        List<RoleDTO> roleList = roleDao.getManyRole();
        for (RoleDTO role : roleList) {
            if (account.getAccountType().equalsIgnoreCase(role.getName())) {
                roleAccountDao.createRoleAccount(role.getId(), account.getId());
                break;
            }
        }
        customerDTO.setIdAccount(account.getId());
        customerDao.createOneCustomer(customerDTO);
        CustomerDTO customer = customerDao.getOneCustomerByIdAccount(account.getId());

        CartDTO cart = new CartDTO();
        cart.setIdCustomer(customer.getId());
        cart.setNotes("empty");
        cart.setStatus("inactive");
        cartService.createOneCart(cart);

        updateAccount(account);
        mailService.sendEmailVerification(customer.getMail(), jwtProvider.generateVerifyToken(customer.getMail()));
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }

    @Override
    public List<AccountDTO> getManyAccount() {
        return accountDao.getManyAccount();
    }

    @Override
    public AccountDTO getOneAccountById(Long id) {
        return accountDao.getOneAccountById(id);
    }

    @Override
    public AccountDTO getOneAccountByIdCustomer(Long idCustomer) {
        return accountDao.getOneAccountByIdCustomer(idCustomer);
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountDTO.setIdCustomer(customerDao.getOneCustomerByIdAccount(accountDTO.getId()).getId());
        accountDao.updateAccount(accountDTO);
    }

    @Override
    public AccountDTO getOneAccountByUserName(String userName) {
        AccountDTO account = accountDao.getOneAccountByUserName(userName);
        return account;
    }

    @Override
    public TupleToken loginAccountWithoutToken(String username, String password, HttpServletRequest request) {
        try {
            Authentication auth = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails user = customUserDetailService.loadUserByUsername(username);
            if (user == null) {
                return null;
            }
            List<GrantedAuthority> listAuthorities = user.getAuthorities().stream().collect(Collectors.toList());
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            String accessToken = "Bearer "
                    + jwtProvider.generateAccessToken(sc.getAuthentication().getName(), listAuthorities);
            String refreshToken = jwtProvider.generateRefreshToken(sc.getAuthentication().getName());
            try {
                createInfoRefreshToken(sc.getAuthentication().getName(), request, refreshToken);
            } catch (JsonMappingException ex) {

            } catch (JsonProcessingException ex) {

            }
            TupleToken tupleToken = new TupleToken();
            tupleToken.setAccessToken(accessToken);
            tupleToken.setRefreshToken(refreshToken);
            return tupleToken;
        } catch (AuthenticationException authenticationException) {
            authenticationException.getMessage();
            return null;
        }
    }

    public void createInfoRefreshToken(String username, HttpServletRequest request, String refreshToken)
            throws JsonMappingException, JsonProcessingException {
        InfoRefreshToken infoRefreshToken = new InfoRefreshToken();

        Claims claims = jwtProvider.validateToken(refreshToken);

        Date dateCreated = claims.getIssuedAt();
        Instant instant = dateCreated.toInstant();
        LocalDateTime createdAt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date dateExpires = claims.getExpiration();
        Instant instant2 = dateExpires.toInstant();
        LocalDateTime expiresAt = instant2.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // TODO: String ip = request.getHeader("X-Forwarded-For");

        infoRefreshToken.setRefreshToken(refreshToken);
        infoRefreshToken.setCreatedAt(createdAt);
        infoRefreshToken.setStatus("active");
        infoRefreshToken.setExpiresAt(expiresAt);
        infoRefreshToken.setIpAddress("192.168.1.1");

        String deviceType = deviceService.detectDevice(request);

        try {
            String jsonValue = redisUtils.getValue(username, deviceType, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            // Them hai dong nay de xu ly ngoai le JsonMappingExpcetion cua kieu du lieu
            // LocalDateTime
            objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module cho java.time
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Không ghi ngày dưới dạng timestamp

            List<InfoRefreshToken> infoRefreshTokenList = null;
            if (jsonValue == null) {
                infoRefreshTokenList = new ArrayList<>();
            } else {
                infoRefreshTokenList = objectMapper.readValue(jsonValue, new TypeReference<List<InfoRefreshToken>>() {
                });
            }
            infoRefreshTokenList.add(infoRefreshToken);
            jsonValue = objectMapper.writeValueAsString(infoRefreshTokenList);
            redisUtils.putValue(username, deviceType, jsonValue);
        } catch (JedisDataException exception) {
            throw new IllegalArgumentException(
                    "Xem lai kieu du lieu cua key, co the da ton tai mot key cung ten nhung khac kieu du lieu");
        }

    }

    @Override
    public TupleToken validateRefreshToken(HttpServletRequest request) {
        String deviceType = deviceService.detectDevice(request);
        String refreshToken = request.getHeader("Authorization2");
        Claims claims = jwtProvider.validateToken(refreshToken);
        // TODO: Xử lý thêm hết hạn refresh token bắt đăng nhập lại
        TupleToken tupleToken = new TupleToken();
        if (claims != null) {
            String userName = claims.getSubject();
            String jsonValue = redisUtils.getValue(userName, deviceType, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module cho java.time
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Không ghi ngày dưới dạng timestamp

            try {
                List<InfoRefreshToken> infoRefreshTokenList = objectMapper.readValue(jsonValue,
                        new TypeReference<List<InfoRefreshToken>>() {
                        });

                List<String> refreshTokenList = infoRefreshTokenList.stream().map(InfoRefreshToken::getRefreshToken)
                        .collect(Collectors.toList());
                if (refreshTokenList.contains(refreshToken)) {
                    if ((infoRefreshTokenList.get(infoRefreshTokenList.size() - 1)).getRefreshToken()
                            .equalsIgnoreCase(refreshToken)) {
                        System.out.println("Refresh token moi nhat hop le de tra ve bo token moi");

                        UserDetails user = customUserDetailService.loadUserByUsername(userName);
                        List<GrantedAuthority> listAuthorities = user.getAuthorities().stream()
                                .collect(Collectors.toList());
                        String newAccessToken = "Bearer "
                                + jwtProvider.generateAccessToken(userName, listAuthorities);
                        String newRefreshToken = jwtProvider.generateRefreshToken(userName);

                        tupleToken.setAccessToken(newAccessToken);
                        tupleToken.setRefreshToken(newRefreshToken);

                        createInfoRefreshToken(userName, request, newRefreshToken); // Luu info vao redis

                    } else {
                        System.out.println("Yeu cau dang nhap lai vi refresh token da cu");
                        tupleToken.setAccessToken("empty");
                        tupleToken.setRefreshToken("empty");
                    }
                } else {
                    System.out.println("Khong co refresh token nay trong bo nho refresh token da bi ban");
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return tupleToken;
    }

    @Override
    public boolean validateExistsUsername(String userName) {
        try {
            AccountDTO account = accountDao.getOneAccountByUserName(userName);
            return true;
        } catch (EntityNotFoundException entityNotFoundException) {
            return false;
        }
    }

    @Override
    public void verifyAccount(String token) {
        Claims claims = jwtProvider.validateToken(token);
        CustomerDTO customer = customerDao.getOneCustomerByMail(claims.getSubject());
        AccountDTO account = accountDao.getOneAccountByIdCustomer(customer.getId());
        account.setStatus("active");
        accountDao.updateAccount(account);
    }

    /*
     * 1. Sau khi người dùng nhập đúng mail tồn tại. Tạo thêm token làm key và
     * idAccount làm value lưu nó vào redis
     * 2. Gửi mail kèm token này đến người dùng
     * 3. Người dùng nhấp vào mail là sẽ gửi token này lại server kiểm tra token này
     * có tồn tại trong redis không. Nếu không thì sẽ từ chối
     * 4. Nếu có tồn tại thì cho phép người dùng nhập mật khẩu mới.
     * 5. Sau khi thay đổi thành công sẽ xóa token khỏi redis
     */
    @Override
    public Boolean forgotPassword(String email) {
        try {
            CustomerDTO customer = customerDao.getOneCustomerByMail(email);
            AccountDTO account = accountDao.getOneAccountByIdCustomer(customer.getId());
            String token = jwtProvider.generateRefreshToken(email);
            redisUtils.setStringWithExpiry(token, account.getId().toString(), 60, TimeUnit.MINUTES);
            mailService.sendEmailForgotPassword(email, token);
            return true;
        } catch (EntityNotFoundException entityNotFoundException) {
            return false;
        }
    }

    @Override
    public void updateAccountToResetPassword(AccountDTO accountDTO) {
        AccountDTO oldAccount = accountDao.getOneAccountById(accountDTO.getId());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        oldAccount.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        accountDao.updateAccount(oldAccount);
    }

    @Override
    public AccessTokenAndIdAccount verifyTokenReset(String tokenReset) {
        if (redisUtils.getString(tokenReset) != null) {
            Long idAccount = Long.parseLong(redisUtils.getString(tokenReset));
            AccountDTO account = accountDao.getOneAccountById(idAccount);
            UserDetails user = customUserDetailService.loadUserByUsername(account.getUserName());
            List<GrantedAuthority> listAuthorities = user.getAuthorities().stream().collect(Collectors.toList());
            String newAccessToken = "Bearer " + jwtProvider.generateAccessToken(account.getUserName(), listAuthorities);
            redisUtils.delete(tokenReset);
            System.out.println("Có thể thay đổi mật khẩu");
            return new AccessTokenAndIdAccount(newAccessToken, idAccount);
        } else {
            return null;
        }
    }
}
