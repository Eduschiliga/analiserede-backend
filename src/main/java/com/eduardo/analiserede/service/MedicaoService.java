package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.entity.Medicao;
import com.eduardo.analiserede.mapper.MedicaoMapper;
import com.eduardo.analiserede.model.dto.MedicaoDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import com.eduardo.analiserede.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicaoService {
  @Autowired
  private MedicaoMapper medicaoMapper;

  @Autowired
  private MedicaoRepository medicaoRepository;

  @Autowired
  private LocalRepository localRepository;

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
    medicao.setLocal(local);

    medicao = medicaoRepository.save(medicao);

    local.getMedicoes().add(medicao);
    localRepository.save(local);

    return new MedicaoDTO(medicao.getId(), medicao.getData(), medicao.getNivel_sinal_24ghz(),
        medicao.getNivel_sinal_5ghz(), medicao.getVelocidade_24ghz(), medicao.getVelocidade_5ghz(),
        medicao.getInterferencia());
  }

  public List<MedicaoDTO> buscarTodos(Long usuarioId) {
    return this.medicaoMapper.medicaoListToMedicaoDTOList(medicaoRepository.findAllByLocal_Usuario_Id(usuarioId));
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
    Medicao medicao = medicaoRepository.findById(medicaoDTO.getId())
        .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    Local local = localRepository.findById(idLocal)
        .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

    medicao.setLocal(local);
    medicao.setData(medicaoDTO.getData());
    medicao.setNivel_sinal_24ghz(medicaoDTO.getNivel_sinal_24ghz());
    medicao.setNivel_sinal_5ghz(medicaoDTO.getNivel_sinal_5ghz());
    medicao.setVelocidade_24ghz(medicaoDTO.getVelocidade_24ghz());
    medicao.setVelocidade_5ghz(medicaoDTO.getVelocidade_5ghz());
    medicao.setInterferencia(medicaoDTO.getInterferencia());

    return this.medicaoMapper.medicaoToMedicaoDTO(medicaoRepository.save(medicao));
  }
}
