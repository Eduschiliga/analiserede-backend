package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.service.UsuarioService;
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
@RequestMapping("/api/usuario")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {
  private UsuarioService usuarioService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public UsuarioDTO criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
    UsuarioDTO usuario = usuarioService.salvar(usuarioDTO);

    URI uri = uriBuilder.path("/api/usuario/{id}").buildAndExpand(usuario.getUsuarioId()).toUri();

    return ResponseEntity.created(uri).body(usuario).getBody();
  }

  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> buscarUsuario() {
    return ResponseEntity.ok().body(usuarioService.buscarTodos());
  }

  @GetMapping("/{idUsuario}")
  public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable @NotNull Long idUsuario) {
    return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(idUsuario));
  }

  @DeleteMapping("/{idUsuario}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable @NotNull Long idUsuario) {
    usuarioService.deletar(idUsuario);
  }

  @PutMapping
  public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
    return ResponseEntity.ok().body(usuarioService.atualizar(usuarioDTO));
  }
}
