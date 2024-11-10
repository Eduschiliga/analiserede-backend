package com.eduardo.analiserede.controller;

import com.eduardo.analiserede.model.dto.AuthenticationDTO;
import com.eduardo.analiserede.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO) {
    return ResponseEntity.ok(authService.login(authenticationDTO));
  }
}
