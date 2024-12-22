package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.LoginRequest;
import com.eduardo.analiserede.model.dto.TokenDTO;
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
}
