package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.LocalMapper;
import com.eduardo.analiserede.mapper.UsuarioMapper;
import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import com.eduardo.analiserede.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalService {
  private final LocalRepository localRepository;
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final LocalMapper localMapper;

  @Autowired
  public LocalService(LocalRepository localRepository, UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, LocalMapper localMapper) {
    this.localRepository = localRepository;
    this.usuarioRepository = usuarioRepository;
    this.usuarioMapper = usuarioMapper;
    this.localMapper = localMapper;
  }

  public LocalDTO salvar(@Valid LocalDTO localDTO, String emailUsuario) {
    Usuario usuario = this.usuarioRepository.findByEmail(emailUsuario);

    if (usuario != null) {
      Local local = this.localMapper.localDTOtoLocal(localDTO);
      local.setUsuario(usuario);

      return this.localMapper.localToLocalDTO(this.localRepository.save(local));
    }
    return null;
  }

  public LocalDTO buscarPorId(Long id) {
    Local local = localRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    return localMapper.localToLocalDTO(local);
  }

  public List<LocalDTO> buscarTodos() {
    return localRepository.findAll().stream()
        .map(localMapper::localToLocalDTO)
        .collect(Collectors.toList());
  }

  public void deletar(Long id) {
    Local local = this.localRepository.findById(id).orElseThrow(() -> new RuntimeException("Local com id:" + id + "Não encontrado"));

    if (local != null) {
      this.localRepository.delete(local);
    }
  }


  public LocalDTO atualizar(@Valid LocalDTO localDTO) {
    Local l = this.localRepository.findById(localDTO.getId()).orElseThrow(() -> new RuntimeException("Local com id:" + localDTO.getId() + "Não encontrado"));

    if (l != null) {
      return this.localMapper.localToLocalDTO(this.localRepository.save(l));
    }
    return null;
  }
}
