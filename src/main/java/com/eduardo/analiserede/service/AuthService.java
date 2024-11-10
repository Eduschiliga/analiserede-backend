package com.eduardo.analiserede.service;

import com.eduardo.analiserede.model.dto.AcessDTO;
import com.eduardo.analiserede.model.dto.AuthenticationDTO;
import com.eduardo.analiserede.model.dto.UserDetailsImpl;
import com.eduardo.analiserede.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  public AcessDTO login(AuthenticationDTO authenticationDTO) {
     try {
      // cria mecanismo de credencial para o spring
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword());

      // prepara mecanismo para autenticacao
      Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

      // busca usuario logado
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      String token = jwtUtils.generateTokenFromUserDetailsImpl(userDetails);

       return new AcessDTO(token);
     } catch (BadCredentialsException e) {
      // TODO: login ou senha invalida
    }
    return null;
  }
}
