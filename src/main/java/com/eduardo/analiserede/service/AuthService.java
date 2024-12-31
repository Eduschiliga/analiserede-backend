package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.UsuarioMapper;
import com.eduardo.analiserede.model.LoginRequest;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;

  private final TokenService tokenService;

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    return usuarioRepository.findByLogin(login);
  }

  public UsuarioDTO findUsuarioByToken(String token) {
    String subject = tokenService.getSubject(token);
    Usuario usuario = usuarioRepository.findByLogin(subject);

    return usuarioMapper.usuarioToUsuarioDTO(usuario);
  }

  public String refreshToken(String token) {
    String subject = tokenService.getSubject(token);
    Usuario usuario = usuarioRepository.findByLogin(subject);

    return tokenService.gerarToken(usuario);
  }

  public String authLogin(@Valid LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken usernamePassAuthToken = new UsernamePasswordAuthenticationToken(
        loginRequest.getLogin(),
        loginRequest.getSenha()
    );

    AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

    Authentication authentication = authenticationManager.authenticate(usernamePassAuthToken);

    return tokenService.gerarToken((Usuario) authentication.getPrincipal());
  }
}


