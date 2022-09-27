package miu.edu.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtHelper {
    private final String secret = "top-secret";
    private final long expiration = 15 * 60 * 60 * 100;
    public String generateToken(String username, Map<String, Object> claims ) {
        var expires = System.currentTimeMillis() + expiration;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(expires))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 60))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public String getUsernameFromToken(String token) {
        String result = null;
        try {
            result = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Map<String, Object> getClaims(String token) {
        Map<String, Object> result = null;
        try {
            result = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String generateServiceToken(String service) {
        return Jwts.builder()
                .setSubject(service)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 100)))
                .signWith(SignatureAlgorithm.HS512, service)
                .compact();
    }

    public boolean validateServiceToken(String service, String token) {
        try {
            Jwts.parser()
                    .setSigningKey(service)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
