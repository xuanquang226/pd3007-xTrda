package security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
@Scope("prototype")
public class JWTProvider {

    @Value("${SECRET_KEY}")
    private String JWT_SECRET_KEY;
    private final long AT_EXPIRE = 450000L;
    private final long VT_EXPIRE = 600000L;
    private final long RT_EXPIRE = 120000000L;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
    }

    public String generateVerifyToken(String mail) {
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + VT_EXPIRE);

        return Jwts.builder()
                .setSubject(mail)
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    };

    public String generateAccessToken(String subject, List<GrantedAuthority> role) {
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + AT_EXPIRE);

        List<String> roleList = role.stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .claim("role", roleList)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + RT_EXPIRE);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .claim("type", "refresh")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Claims validateToken(String token) throws MalformedJwtException, ExpiredJwtException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
