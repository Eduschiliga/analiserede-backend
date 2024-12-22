package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.entity.Medicao;
import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.LocalMapper;
import com.eduardo.analiserede.mapper.MedicaoMapper;
import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import com.eduardo.analiserede.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocalService {
  private final LocalRepository localRepository;
  private final UsuarioRepository usuarioRepository;
  private final LocalMapper localMapper;
  private final MedicaoMapper medicaoMapper;
  private final MedicaoService medicaoService;

  public LocalDTO salvar(@Valid LocalDTO localDTO, String emailUsuario) {
    Usuario usuario = this.usuarioRepository.findByEmail(emailUsuario);

    if (usuario != null) {
      Local local = this.localMapper.localDTOtoLocal(localDTO);
      local.setUsuario(usuario);

      List<Medicao> medicaoTemp = null;
      if (localDTO.getMedicoes() != null) {
        medicaoTemp = local.getMedicoes();
        local.setMedicoes(new ArrayList<>());
      }

      localDTO = this.localMapper.localToLocalDTO(this.localRepository.save(local));

      if (medicaoTemp != null) {
        for (Medicao m : medicaoTemp) {
          this.medicaoService.criarMedicao(this.medicaoMapper.medicaoToMedicaoDTO(m), localDTO.getId());
        }
        localDTO.setMedicoes(this.medicaoMapper.medicaoListToMedicaoDTOList(medicaoTemp));
      }

      return localDTO;
    }
    return null;
  }

  public LocalDTO buscarPorId(Long id) {
    Local local = localRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    return localMapper.localToLocalDTO(local);
  }

  public List<LocalDTO> buscarTodos(Long usuarioId) {
    return localRepository.findAllByUsuario_UsuarioId(usuarioId).stream().map(localMapper::localToLocalDTO).collect(Collectors.toList());
  }

  public void deletar(Long id) {
    Local local = this.localRepository.findById(id).orElseThrow(() -> new RuntimeException("Local com id:" + id + "Não encontrado"));

    if (local != null) {
      this.localRepository.delete(local);
    }
  }


  public LocalDTO atualizar(LocalDTO localDTO) {
    if (localDTO.getId() == null) {
      throw new IllegalArgumentException("ID não pode ser nulo para atualizar Local.");
    }

    Local local = this.localRepository.findById(localDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    local.setNome(localDTO.getNome());


    List<Medicao> medicaoTemp = null;
    if (localDTO.getMedicoes() != null) {
      Local localTemp = this.localMapper.localDTOtoLocal(localDTO);
      medicaoTemp = localTemp.getMedicoes();
      local.setMedicoes(new ArrayList<>());
    }

    localDTO = this.localMapper.localToLocalDTO(this.localRepository.save(local));


    List<Medicao> medicaoAdicionada = new ArrayList<>();
    if (medicaoTemp != null) {
      local.setMedicoes(new ArrayList<>());
      for (Medicao m : medicaoTemp) {
        if (m.getMedicaoId() == null) {
          medicaoAdicionada.add(this.medicaoMapper.medicaoDTOtoMedicao(this.medicaoService.criarMedicao(this.medicaoMapper.medicaoToMedicaoDTO(m), localDTO.getId())));
        } else {
          medicaoAdicionada.add(this.medicaoMapper.medicaoDTOtoMedicao(this.medicaoService.atualizar(this.medicaoMapper.medicaoToMedicaoDTO(m), localDTO.getId())));
        }
      }
      localDTO.setMedicoes(this.medicaoMapper.medicaoListToMedicaoDTOList(medicaoAdicionada));
    }


    return localDTO;
  }
}
