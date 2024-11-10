package com.eduardo.analiserede.security.jwt;

import com.eduardo.analiserede.model.dto.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${analiserede.jwtSecret}")
  private String jwtSecret;

  @Value("${analiserede.jwtExpirationMs}")
  private Integer jwtExpirationMs;

  public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, getSigningKey()).compact();
  }

  public Key getSigningKey() {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    return key;
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      e.printStackTrace();
    } catch (ExpiredJwtException e) {
      e.printStackTrace();
    } catch (UnsupportedJwtException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return false;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody().getSubject();
  }
}
