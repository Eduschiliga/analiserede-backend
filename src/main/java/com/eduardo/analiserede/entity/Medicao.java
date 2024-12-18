package com.eduardo.analiserede.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicao")
public class Medicao {
  @Id
  @Column(name = "id_medicao")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "data_medicao")
  private LocalDate data;

  @Column(name = "nivel_sinal_24ghz_medicao")
  private Integer nivel_sinal_24ghz;

  @Column(name = "nivel_sinal_5ghz_medicao")
  private Integer nivel_sinal_5ghz;

  @Column(name = "velocidade_24ghz_medicao")
  private Float velocidade_24ghz;

  @Column(name = "velocidade_5ghz_medicao")
  private Float velocidade_5ghz;

  @Column(name = "interferencia_medicao")
  private Integer interferencia;

  @ManyToOne
  @JoinColumn(name = "local_id")
  @JsonBackReference
  private Local local;
}