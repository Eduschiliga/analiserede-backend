package com.eduardo.analiserede.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.exception.customizadas.TokenJWTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private String expiration;


  public String gerarToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.create()
          .withIssuer("Api de Análise de Redes")
          .withSubject(usuario.getLogin())
          .withExpiresAt(dataExpiracao())
          .sign(algorithm);

    } catch (JWTCreationException e) {
      throw new JWTCreationException("Erro ao criar token", e);
    }
  }

  public String getSubject(String tokenJWT) {
    try {
      return JWT.require(Algorithm.HMAC256(secret))
          .withIssuer("Api de Análise de Redes")
          .build()
          .verify(tokenJWT)
          .getSubject();

    } catch (JWTVerificationException e) {
      throw new TokenJWTException("Erro ao verificar token", HttpStatus.UNAUTHORIZED);
    }
  }

  public boolean isTokenExpirado(String tokenJWT) {
    DecodedJWT decodedJWT = JWT.decode(tokenJWT);

    Date dataExpiracao = decodedJWT.getExpiresAt();

    return dataExpiracao.before(new Date());
  }

  private Instant dataExpiracao() {
    long segundos = Long.parseLong(expiration) * 1000;
    return LocalDateTime.now().plusSeconds(segundos).toInstant(ZoneOffset.of("-03:00"));
  }
}
