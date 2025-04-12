package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.EnderecoDTO;
import com.eduardo.analiserede.service.CepService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CepController {
    private final CepService cepService;

    @GetMapping(value = "/{cep}/consulta")
    public ResponseEntity<EnderecoDTO> handleConsultarCep(@PathVariable String cep) {
        return ResponseEntity.ok().body(cepService.consultar(cep));
    }

    @GetMapping(value = "/{enderecoId}")
    public ResponseEntity<EnderecoDTO> handleBuscarEnderecoPorId(@PathVariable Long enderecoId) {
        return ResponseEntity.ok().body(cepService.consultarCep(enderecoId));
    }
}
