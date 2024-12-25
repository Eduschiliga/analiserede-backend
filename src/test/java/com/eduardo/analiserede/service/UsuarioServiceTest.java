package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.UsuarioMapper;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {
  @InjectMocks
  private UsuarioService usuarioService;

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private UsuarioMapper usuarioMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private Usuario usuario;

  @Mock
  private UsuarioDTO usuarioDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Deve salvar um usuário")
  void salvar() {
    when(usuarioMapper.usuarioDTOtoUsuario(usuarioDTO)).thenReturn(usuario);
    when(passwordEncoder.encode(usuario.getSenha())).thenReturn("senhaCriptografada");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    usuarioService.salvar(usuarioDTO);

    verify(usuarioMapper).usuarioDTOtoUsuario(usuarioDTO);
    verify(passwordEncoder).encode(usuario.getSenha());
    verify(usuarioRepository).save(usuario);
  }

  @Test
  @DisplayName("Deve atualizar um usuário")
  void atualizar() {
    when(usuarioRepository.findById(usuarioDTO.getUsuarioId())).thenReturn(Optional.of(usuario));
    when(usuarioMapper.usuarioDTOtoUsuario(usuarioDTO)).thenReturn(usuario);
    when(passwordEncoder.encode(usuarioDTO.getSenha())).thenReturn("senhaCriptografada");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    usuarioService.atualizar(usuarioDTO);

    verify(usuarioRepository).findById(usuarioDTO.getUsuarioId());
    verify(usuarioMapper).usuarioDTOtoUsuario(usuarioDTO);
    verify(passwordEncoder).encode(usuarioDTO.getSenha());
    verify(usuarioRepository).save(usuario);

    Assertions.assertDoesNotThrow(() -> usuarioService.atualizar(usuarioDTO));
    Assertions.assertNotNull(usuario);
  }

  @Test
  @DisplayName("Deve deletar um usuário")
  void deletar() {
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

    usuarioService.deletar(1L);

    verify(usuarioRepository).findById(1L);
    verify(usuarioRepository).delete(usuario);
    Assertions.assertDoesNotThrow(() -> usuarioService.deletar(1L));
  }

  @Test
  @DisplayName("Deve buscar um usuário por id")
  void buscarUsuarioPorId() {
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    when(usuarioMapper.usuarioToUsuarioDTO(usuario)).thenReturn(usuarioDTO);

    usuarioService.buscarUsuarioPorId(1L);

    verify(usuarioRepository).findById(1L);
    verify(usuarioMapper).usuarioToUsuarioDTO(usuario);

    Assertions.assertDoesNotThrow(() -> usuarioService.buscarUsuarioPorId(1L));
    Assertions.assertNotNull(usuarioDTO);
  }
}