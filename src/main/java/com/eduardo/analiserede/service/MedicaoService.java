package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.entity.Medicao;
import com.eduardo.analiserede.mapper.MedicaoMapper;
import com.eduardo.analiserede.model.dto.MedicaoDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import com.eduardo.analiserede.repository.MedicaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicaoService {
  private final MedicaoMapper medicaoMapper;
  private final MedicaoRepository medicaoRepository;
  private final LocalRepository localRepository;

  public MedicaoDTO criarMedicao(MedicaoDTO medicaoDTO, Long idLocal) {
    Local local = localRepository.findById(idLocal)
        .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    Medicao medicao = new Medicao();
    medicao.setData(medicaoDTO.getData());
    medicao.setNivel_sinal_24ghz(medicaoDTO.getNivel_sinal_24ghz());
    medicao.setNivel_sinal_5ghz(medicaoDTO.getNivel_sinal_5ghz());
    medicao.setVelocidade_24ghz(medicaoDTO.getVelocidade_24ghz());
    medicao.setVelocidade_5ghz(medicaoDTO.getVelocidade_5ghz());
    medicao.setInterferencia(medicaoDTO.getInterferencia());

    medicao = medicaoRepository.save(medicao);

    local.getMedicoes().add(medicao);
    localRepository.save(local);

    return new MedicaoDTO(medicao.getMedicaoId(), medicao.getData(), medicao.getNivel_sinal_24ghz(),
        medicao.getNivel_sinal_5ghz(), medicao.getVelocidade_24ghz(), medicao.getVelocidade_5ghz(),
        medicao.getInterferencia());
  }

  public List<MedicaoDTO> buscarTodos(Long usuarioId) {
    return this.medicaoMapper.medicaoListToMedicaoDTOList(medicaoRepository.findAllByLocal_Usuario_UsuarioId(usuarioId));
  }

  public MedicaoDTO buscarPorId(Long id) {
    return this.medicaoMapper.medicaoToMedicaoDTO(medicaoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Medição não encontrada")));
  }

  public void deletar(Long idMedicao) {
    Medicao medicao = medicaoRepository.findById(idMedicao)
        .orElseThrow(() -> new IllegalArgumentException("Medição não encontrada"));

    medicaoRepository.delete(medicao);
  }

  public MedicaoDTO atualizar(MedicaoDTO medicaoDTO, Long idLocal) {
    Medicao medicao = medicaoRepository.findById(medicaoDTO.getMedicaoId())
        .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    medicao.setData(medicaoDTO.getData());
    medicao.setNivel_sinal_24ghz(medicaoDTO.getNivel_sinal_24ghz());
    medicao.setNivel_sinal_5ghz(medicaoDTO.getNivel_sinal_5ghz());
    medicao.setVelocidade_24ghz(medicaoDTO.getVelocidade_24ghz());
    medicao.setVelocidade_5ghz(medicaoDTO.getVelocidade_5ghz());
    medicao.setInterferencia(medicaoDTO.getInterferencia());

    return this.medicaoMapper.medicaoToMedicaoDTO(medicaoRepository.save(medicao));
  }
}
