package com.eduardo.analiserede.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
  private Long usuarioId;
  private String email;
  private String login;
  private String senha;
  private String nomeCompleto;
}
