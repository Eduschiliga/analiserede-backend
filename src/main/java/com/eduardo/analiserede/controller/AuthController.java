package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.AuthenticationDTO;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.service.AuthService;
import com.eduardo.analiserede.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
  @Autowired
  private AuthService authService;

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO) {
    return ResponseEntity.ok(authService.login(authenticationDTO));
  }

  @PostMapping("/registrar")
  public UsuarioDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
    return usuarioService.salvar(usuarioDTO);
  }
}
