package com.eduardo.analiserede.mapper;

import com.eduardo.analiserede.entity.Endereco;
import com.eduardo.analiserede.model.dto.EnderecoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco enderecoDtotoEndereco(EnderecoDTO enderecoDto);

    EnderecoDTO enderecoToEnderecoDTO(Endereco endereco);

    List<Endereco> enderecoDtoListToEnderecoList(List<EnderecoDTO> enderecoDtoList);
}
