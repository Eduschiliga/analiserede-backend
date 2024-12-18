package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.MedicaoDTO;
import com.eduardo.analiserede.service.MedicaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicao")
public class MedicaoController {

  private final MedicaoService medicaoService;

  public MedicaoController(MedicaoService medicaoService) {
    this.medicaoService = medicaoService;
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public MedicaoDTO criarMedicao(@RequestBody @Valid MedicaoDTO medicaoDTO, @RequestHeader Long idLocal) {
    return medicaoService.criarMedicao(medicaoDTO, idLocal);
  }

  @GetMapping("usuario/{usuarioId}")
  public ResponseEntity<List<MedicaoDTO>> buscarLocais(@PathVariable Long usuarioId) {
    return ResponseEntity.ok().body(medicaoService.buscarTodos(usuarioId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MedicaoDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok().body(medicaoService.buscarPorId(id));
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
