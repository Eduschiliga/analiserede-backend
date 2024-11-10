package com.eduardo.analiserede.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Local {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "nome_local", nullable = false, length = 180)
  private String nome;

  @OneToMany(mappedBy = "local", cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH
  })
  private List<Medicao> medicoes = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  @NotNull
  private Usuario usuario;
}
