package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.service.LocalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local")
@CrossOrigin(origins = "*")
public class LocalController {

  private final LocalService localService;

  @Autowired
  public LocalController(LocalService localService) {
    this.localService = localService;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public LocalDTO criarLocal(@RequestBody @Valid LocalDTO local, @RequestHeader("emailUsuario") String emailUsuario) {
    return localService.salvar(local, emailUsuario);
  }

  @GetMapping("/usuario/{usuarioId}")
  public ResponseEntity<List<LocalDTO>> buscarLocais(@PathVariable Long usuarioId) {
    return ResponseEntity.ok().body(localService.buscarTodos(usuarioId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LocalDTO> buscarPorId(@PathVariable @NotNull Long id) {
    return ResponseEntity.ok().body(localService.buscarPorId(id));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deletarLocal(@PathVariable @NotNull Long id) {
    localService.deletar(id);
  }

  @PutMapping
  public ResponseEntity<LocalDTO> atualizarLocal(@RequestBody @Valid LocalDTO localDTO) {
    return ResponseEntity.ok().body(localService.atualizar(localDTO));
  }
}
