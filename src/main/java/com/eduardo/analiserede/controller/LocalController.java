package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.service.LocalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/local")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LocalController {
  private final LocalService localService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<LocalDTO> criarLocal(@RequestBody @Valid LocalDTO local, @RequestHeader("emailUsuario") String emailUsuario, UriComponentsBuilder uriBuilder) {
    LocalDTO localDTO = localService.salvar(local, emailUsuario);

    URI uri = uriBuilder.path("/api/local/{id}").buildAndExpand(localDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(localDTO);
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
