package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.model.dto.UserDetailsImpl;
import com.eduardo.analiserede.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
    return UserDetailsImpl.build(usuario.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado com o login: " + username)));
  }
}
