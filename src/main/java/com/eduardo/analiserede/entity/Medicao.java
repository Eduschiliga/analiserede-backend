package com.eduardo.analiserede.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "medicao")
public class Medicao {
  @Id
  @Column(name = "medicao_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long medicaoId;

  @Column(name = "data")
  private LocalDate data;

  @Column(name = "nivel_sinal_24ghz")
  private Integer nivel_sinal_24ghz;

  @Column(name = "nivel_sinal_5ghz")
  private Integer nivel_sinal_5ghz;

  @Column(name = "velocidade_24ghz")
  private Float velocidade_24ghz;

  @Column(name = "velocidade_5ghz")
  private Float velocidade_5ghz;

  @Column(name = "interferencia")
  private Integer interferencia;

  @ManyToOne
  @JoinColumn(name = "local_id")
  @JsonBackReference
  private Local local;
}