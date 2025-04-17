package com.sonora.sonoraapi.controllers;

import com.sonora.sonoraapi.dtos.*;
import com.sonora.sonoraapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterDTO dto) {
        try {
            AuthResponseDTO response = authService.register(dto);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(AuthResponseDTO.builder()
                            .message(e.getReason())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponseDTO.builder()
                            .message("Erro ao registrar o usuário: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO dto) {
        try {
            AuthResponseDTO response = authService.authenticate(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponseDTO.builder()
                            .message("Erro ao autenticar o usuário: " + e.getMessage())
                            .build());
        }
    }
}
