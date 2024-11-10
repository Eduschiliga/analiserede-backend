package com.eduardo.analiserede.mapper;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
  Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO);
  UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
  List<Usuario> usuarioDTOListToUsuarioList(List<UsuarioDTO> usuarioDTOList);
  List<UsuarioDTO> usuarioListToUsuarioDTOList(List<Usuario> usuarioList);
}
