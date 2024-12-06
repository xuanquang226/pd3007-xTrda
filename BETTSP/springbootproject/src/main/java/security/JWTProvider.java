package security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
@Scope("prototype")
public class JWTProvider {
    private final String JWT_SECRET = "quanggggggggggggggggxuangquanggggggggggg";
    private final long AT_EXPIRE = 85000L;
    private final long RT_EXPIRE = 120000000L;
    private final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

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
