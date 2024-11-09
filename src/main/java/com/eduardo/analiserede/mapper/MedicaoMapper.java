package com.eduardo.analiserede.mapper;

import com.eduardo.analiserede.entity.Medicao;
import com.eduardo.analiserede.model.dto.MedicaoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicaoMapper {
  Medicao medicaoDTOtoMedicao(MedicaoDTO MedicaoDTO);

  MedicaoDTO medicaoToMedicaoDTO(Medicao medicao);

  List<Medicao> medicaoDTOListToMedicaoList(List<MedicaoDTO> medicaoDTOList);

  List<MedicaoDTO> medicaoListToMedicaoDTOList(List<Medicao> medicaoList);
}
