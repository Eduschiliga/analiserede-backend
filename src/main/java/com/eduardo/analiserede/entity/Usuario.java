package com.eduardo.analiserede.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name ="email_usuario", nullable = false, unique = true)
  private String email;

  @Column(name = "senha_usuario", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String senha;

  @Column(name = "nome_completo_usuario")
  private String nomeCompleto;

  @Column(name = "login_usuario", nullable = false, unique = true)
  private String login;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Local> locais = new ArrayList<>();

}
