package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public UsuarioDTO criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
    return usuarioService.salvar(usuarioDTO);
  }

  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> buscarUsuario() {
    return ResponseEntity.ok().body(usuarioService.buscarTodos());
  }

  @GetMapping("/{idUsuario}")
  public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable @NotNull Long idUsuario) {
    return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(idUsuario));
  }

  @GetMapping("/email")
  public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestHeader("emailUsuario") String emailUsuario) {
    return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorEmail(emailUsuario));
  }

  @GetMapping("/emailsenha")
  public ResponseEntity<UsuarioDTO> buscarPorEmailESenha(@RequestHeader("emailUsuario") String emailUsuario, @RequestHeader("senhaUsuario") String senhaUsuario) {
    return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorEmailESenha(emailUsuario, senhaUsuario));
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
