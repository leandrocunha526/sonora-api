package com.sonora.sonoraapi.services;

import com.sonora.sonoraapi.dtos.AuthRequestDTO;
import com.sonora.sonoraapi.dtos.AuthResponseDTO;
import com.sonora.sonoraapi.dtos.RegisterDTO;
import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.repositories.UserRepository;
import com.sonora.sonoraapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Registro
    public AuthResponseDTO register(RegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de usuário já está em uso.");
        }

        if (userRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já está em uso.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .cpf(dto.getCpf())
                .password(encodedPassword)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .message("Registro realizado com sucesso")
                .build();
    }

    // Login
    public AuthResponseDTO authenticate(AuthRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .message("Login realizado com sucesso")
                .build();
    }
}