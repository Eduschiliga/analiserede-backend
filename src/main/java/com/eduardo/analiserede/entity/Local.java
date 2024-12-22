package com.eduardo.analiserede.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "local")
public class Local {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "local_id")
  private Long localId;

  @Column(name = "nome", length = 180)
  private String nome;

  @OneToMany(mappedBy = "local", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Medicao> medicoes = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  @NotNull
  private Usuario usuario;
}
