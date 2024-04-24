package lv.velotakas.app.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lv.velotakas.app.models.enums.Role;
import lv.velotakas.app.service.JwtService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "qg26mdEZ7kqF7h2iDGgHGwcfjd5e7disT6dbgwISYt8fB3UW0wDH6JaIrJnHxSxQGid9hBp7c8avlWDDdSxvphEqRwdhczBio0GgGMkh0HH8VhoQhb1bKckjquSElrk1EpRqTA56HsiP1tRirw8rM2MKhUH2mRwCej136fQQ3oIosCuT4vEaAN2KleWZsVBWmOpc41y6uGWdYxtPvybwntpWSRjApYxNtyQXFNPqYqe17IlLrZkl6kuvgrMzeLbc";
    private static final long EXPIRATION_DATE = 1000 * 60 * 24; //TODO move to secret folder or some shit = minute to hour to day
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        log.info(String.valueOf(userDetails));
        Map<String, Object> extraClaim = new HashMap<>();
        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.AUTHENTICATED.name()))){
            extraClaim.put("role", "authenticated");
        }
        else{
            extraClaim.put("role", "admin");
        }
        return generateToken(extraClaim, userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
