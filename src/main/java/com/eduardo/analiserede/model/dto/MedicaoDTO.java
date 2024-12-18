package com.eduardo.analiserede.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicaoDTO {
  private Long id;
  private LocalDate data;
  private Integer nivel_sinal_24ghz;
  private Integer nivel_sinal_5ghz;
  private Float velocidade_24ghz;
  private Float velocidade_5ghz;
  private Integer interferencia;
}
