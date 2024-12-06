package security;

import java.io.IOException;
import java.lang.System.Logger.Level;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AccountService;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AccountService accountService;

    // @Autowired
    // private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getAccessTokenFromRequest(request);
        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtProvider.validateToken(token);
                String username = claims.getSubject();
                List<String> roleNameList = claims.get("role", List.class);
                if (roleNameList != null) {
                    List<GrantedAuthority> roleList = roleNameList.stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, roleList);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext sc = SecurityContextHolder.getContext();
                    sc.setAuthentication(authenticationToken);
                } else {
                    throw new NullPointerException("Can't use refresh token to access");
                }
            } catch (MalformedJwtException ex) {
                System.getLogger(JWTAuthenticationFilter.class.getName()).log(Level.INFO, "Invalid token", ex);
            } catch (ExpiredJwtException ex) {
                System.getLogger(JWTAuthenticationFilter.class.getName()).log(Level.INFO, "Expired token", ex);
                // hết hạn yêu cầu đăng nhập lại
                // gần hết hạn sẽ sử dụng refresh token để cấp lại cặp token mới
                // response.addHeader("Access-Token-Expires", "true");

                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // tra ve cho frontend status code 403
                response.getWriter().write("JWT token has expired.");
                return;

            } catch (NullPointerException ex) {
                System.getLogger(JWTAuthenticationFilter.class.getName()).log(Level.INFO, ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
