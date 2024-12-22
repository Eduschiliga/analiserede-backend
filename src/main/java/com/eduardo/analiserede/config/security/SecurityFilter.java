package com.eduardo.analiserede.config.security;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.repository.UsuarioRepository;
import com.eduardo.analiserede.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String tokenJWT = recuperarToken(request);

    if (tokenJWT != null) {
      String subject = tokenService.getSubject(tokenJWT);

      Usuario usuario = usuarioRepository.findByLogin(subject);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token != null) {
      return token.replace("Bearer ", "");
    }
    return null;
  }
}
