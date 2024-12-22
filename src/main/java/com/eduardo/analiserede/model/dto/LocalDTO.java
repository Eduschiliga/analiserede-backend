package com.eduardo.analiserede.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalDTO {
  private Long localId;
  private String nome;
  private List<MedicaoDTO> medicoes;
}
