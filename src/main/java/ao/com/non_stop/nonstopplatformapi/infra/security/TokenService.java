package ao.com.non_stop.nonstopplatformapi.infra.security;

import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expirationDate;

    public String generateToken(User user, String role){
        return Jwts.builder()
                .claim("ROLE",role)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    public boolean isTokenValid(String token, User user){
        String email = extractEmail(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }

    public String validateToken(String token){
        return Jwts.claims().getSubject();
    }

    public Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractEmail(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

}
