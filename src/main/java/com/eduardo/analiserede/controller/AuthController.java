package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.TokenDTO;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.model.request.LoginRequest;
import com.eduardo.analiserede.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
  private final AuthService authService;

  @PostMapping()
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
    TokenDTO tokenDTO = new TokenDTO(authService.authLogin(loginRequest));

    return ResponseEntity.ok().body(tokenDTO);
  }

  @GetMapping("refresh-token/{token}")
  public ResponseEntity<TokenDTO> refreshToken(@PathVariable String token) {
    TokenDTO tokenDTO = new TokenDTO(authService.refreshToken(token));

    return ResponseEntity.ok().body(tokenDTO);
  }

  @GetMapping("token/{token}")
  public ResponseEntity<UsuarioDTO> loadUserByToken(@PathVariable String token) {
    return ResponseEntity.ok().body(authService.findUsuarioByToken(token));
  }
}
