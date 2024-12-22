package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.MedicaoDTO;
import com.eduardo.analiserede.service.MedicaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicao")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MedicaoController {
  private final MedicaoService medicaoService;

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<MedicaoDTO> criarMedicao(@RequestBody @Valid MedicaoDTO medicaoDTO, @RequestHeader Long idLocal, UriComponentsBuilder uriBuilder) {
    MedicaoDTO medicao = medicaoService.criarMedicao(medicaoDTO, idLocal);

    URI uri = uriBuilder.path("/api/medicao/{medicaoId}").buildAndExpand(medicao.getMedicaoId()).toUri();
    return ResponseEntity.created(uri).body(medicao);
  }

  @GetMapping("usuario/{usuarioId}")
  public ResponseEntity<List<MedicaoDTO>> buscarLocais(@PathVariable Long usuarioId) {
    return ResponseEntity.ok().body(medicaoService.buscarTodos(usuarioId));
  }

  @GetMapping("/{medicaoId}")
  public ResponseEntity<MedicaoDTO> buscarPorId(@PathVariable Long medicaoId) {
    return ResponseEntity.ok().body(medicaoService.buscarPorId(medicaoId));
  }

  @PutMapping("local/{localId}")
  public ResponseEntity<MedicaoDTO> atualizarMedicao(@RequestBody @Valid MedicaoDTO medicaoDTO, @PathVariable Long localId) {
    return ResponseEntity.ok().body(medicaoService.atualizar(medicaoDTO, localId));
  }

  @DeleteMapping("/{idMedicao}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deletarMedicao(@PathVariable Long idMedicao) {
    medicaoService.deletar(idMedicao);
  }
}
