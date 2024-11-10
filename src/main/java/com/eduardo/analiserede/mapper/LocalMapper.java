package com.eduardo.analiserede.mapper;


import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.model.dto.LocalDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalMapper {
  Local localDTOtoLocal(LocalDTO localDTO);

  LocalDTO localToLocalDTO(Local local);

  List<Local> localDTOListToLocalList(List<LocalDTO> localDTOList);

  List<LocalDTO> localListToLocalDTOList(List<Local> localList);
}
