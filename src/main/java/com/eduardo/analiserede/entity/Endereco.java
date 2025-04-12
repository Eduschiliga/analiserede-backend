package com.eduardo.analiserede.entity;

import com.eduardo.analiserede.model.enums.StatusEndereco;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "endereco")
@RequiredArgsConstructor
@Getter
@Setter
public class Endereco {

    @Id
    @Column(name = "endereco_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enderecoId;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "unidade")
    private String unidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "estado")
    private String estado;

    @Column(name = "regiao")
    private String regiao;

    @Column(name = "ibge")
    private String ibge;

    @Column(name = "gia")
    private String gia;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "siafi")
    private String siafi;

    @Column(name = "status_consulta")
    private StatusEndereco statusConsulta = StatusEndereco.NA;
}
