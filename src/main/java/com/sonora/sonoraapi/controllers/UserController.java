package com.sonora.sonoraapi.controllers;

import com.sonora.sonoraapi.dtos.UpdateUserDTO;
import com.sonora.sonoraapi.dtos.UserDTO;
import com.sonora.sonoraapi.entities.Role;
import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Lista todos os usuários (somente para ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(),
                        user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

    // Busca um usuário pelo ID (permitido para o dono ou ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        // Verifica se o usuário existe no banco de dados
        UserDTO userDTO = userService.getUserById(id);

        // Se o usuário não for encontrado, lança uma exceção
        if (userDTO == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return ResponseEntity.ok(userDTO);
    }

    // Cria um novo usuário
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setCpf(userDTO.getCpf());
        user.setRole(Role.valueOf(userDTO.getRole()));

        // Salva o usuário no banco de dados
        User createdUser = userService.createUser(user);

        // Retorna o DTO com os dados do usuário criado
        return ResponseEntity.status(201).body(new UserDTO(createdUser.getId(), createdUser.getName(),
                createdUser.getUsername(), createdUser.getCpf(), createdUser.getRole().name()));
    }

    // Edita um usuário (ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserDTO updatedUserDTO) {

        // Atualiza o usuário no banco de dados
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(updatedUserDTO.getName());
        updatedUser.setUsername(updatedUserDTO.getUsername());
        updatedUser.setPassword(updatedUserDTO.getPassword());
        updatedUser.setCpf(updatedUserDTO.getCpf());
        updatedUser.setRole(Role.valueOf(updatedUserDTO.getRole()));

        User user = userService.updateUser(id, updatedUser);

        // Retorna o DTO com os dados do usuário atualizado
        return ResponseEntity.ok(
                new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(), user.getRole().name()));
    }

    // Deleta um usuário (ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        // Deleta o usuário do banco de dados
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
