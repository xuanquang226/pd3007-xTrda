package security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import data.dao.AccountDao;
import data.dao.RoleAccountDao;
import data.dto.AccountDTO;
import data.dto.RoleAccountDTO;
import services.RoleService;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RoleAccountDao roleAccountDao;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDTO account = accountDao.getOneAccountByUserName(username);
        List<RoleAccountDTO> roleAccount = roleAccountDao.getManyRoleAccountByIdAccount(account.getId());
        account.setRoleAccountList(roleAccount);
        return new User(account.getUserName(), account.getPassword(), lAuthorities(account.getRoleAccountList()));
    }

    public List<GrantedAuthority> lAuthorities(List<RoleAccountDTO> roles) {
        List<Long> idRoleList = roles.stream().map(RoleAccountDTO::getId).collect(Collectors.toList());
        List<String> nameRoleList = roleService.getManyRoleNameByIdIn(idRoleList);
        return nameRoleList.stream().map(nameRole -> new SimpleGrantedAuthority("ROLE_" + nameRole))
                .collect(Collectors.toList());
    }

}
