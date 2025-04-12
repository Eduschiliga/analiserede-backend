package com.eduardo.analiserede.model.dto;

import com.eduardo.analiserede.model.enums.StatusEndereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {
    private Long enderecoId;
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private StatusEndereco statusConsulta;
}
