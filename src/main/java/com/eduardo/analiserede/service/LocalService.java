package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.mapper.LocalMapper;
import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalService {
  private final LocalRepository localRepository;
  private final LocalMapper localMapper;

  @Autowired
  public LocalService(LocalRepository localRepository, LocalMapper localMapper) {
    this.localRepository = localRepository;
    this.localMapper = localMapper;
  }

  public LocalDTO salvar(@Valid LocalDTO local) {
    return this.localMapper.localToLocalDTO(this.localRepository.save(this.localMapper.localDTOtoLocal(local)));
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
