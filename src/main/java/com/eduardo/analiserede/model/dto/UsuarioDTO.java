package com.eduardo.analiserede.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
  private Long usuarioId;
  private String email;
  private String login;
  private String senha;
  private String nomeCompleto;
}
