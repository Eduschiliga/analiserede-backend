package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.UsuarioMapper;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {
  private UsuarioRepository usuarioRepository;
  private UsuarioMapper usuarioMapper;

  private final PasswordEncoder passwordEncoder;

  public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
    Usuario usuario = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);

    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

    return usuarioMapper.usuarioToUsuarioDTO(usuarioRepository.save(usuario));
  }

  public List<UsuarioDTO> buscarTodos() {
    return usuarioMapper.usuarioListToUsuarioDTOList(usuarioRepository.findAll());
  }

  public UsuarioDTO atualizar(UsuarioDTO usuarioDTO) {
    Usuario usuario = usuarioRepository.findById(usuarioDTO.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuário com id:" + usuarioDTO.getUsuarioId() + "Não encontrado"));

    if (usuario != null) {
      Usuario usuarioAtualizar = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);

      usuarioAtualizar.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
      return usuarioMapper.usuarioToUsuarioDTO(usuarioRepository.save(usuarioAtualizar));
    }
    return null;
  }

  public void deletar(Long idUsuario) {
    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Local com id:" + idUsuario + "Não encontrado"));

    if (usuario != null) {
      usuarioRepository.delete(usuario);
    }
  }

  public UsuarioDTO buscarUsuarioPorId(Long idUsuario) {
    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Local com id:" + idUsuario + "Não encontrado"));
    return usuarioMapper.usuarioToUsuarioDTO(usuario);
  }

  public UsuarioDTO buscarUsuarioPorEmail(String emailUsuario) {
    Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
    return usuarioMapper.usuarioToUsuarioDTO(usuario);
  }

  public UsuarioDTO buscarUsuarioPorEmailESenha(String emailUsuario, String senhaUsuario) {
    Usuario usuario = usuarioRepository.findByEmailAndSenha(emailUsuario, senhaUsuario);
    return usuarioMapper.usuarioToUsuarioDTO(usuario);
  }
}
