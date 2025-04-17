package com.sonora.sonoraapi.controllers;

import com.sonora.sonoraapi.dtos.UserDTO;
import com.sonora.sonoraapi.entities.Role;
import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(),
                        user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Visualiza o perfil do usuário autenticado
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(),
                user.getRole().name());
        return ResponseEntity.ok(userDTO);
    }

    // Busca um usuário pelo ID (permitido para o dono ou ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userController.isUserOwnData(authentication, #id)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id, Authentication authentication) {
        // Verifica se o usuário existe no banco de dados
        UserDTO userDTO = userService.getUserById(id);

        // Se o usuário não for encontrado, lança uma exceção
        if (userDTO == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return ResponseEntity.ok(userDTO);
    }

    // Cria um novo usuário (somente para ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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

    // Edita um usuário (somente o próprio usuário ou ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userController.isUserOwnData(authentication, #id)")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUserDTO,
            Authentication authentication) {
        // Verifica se o nome do usuário autenticado corresponde ao nome do usuário na
        // URL
        if (!isUserOwnData(authentication, updatedUserDTO.getName())) {
            throw new RuntimeException("Acesso não autorizado");
        }

        // Atualiza o usuário no banco de dados
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(updatedUserDTO.getName());
        updatedUser.setUsername(updatedUserDTO.getUsername());
        updatedUser.setCpf(updatedUserDTO.getCpf());
        updatedUser.setRole(Role.valueOf(updatedUserDTO.getRole()));

        User user = userService.updateUser(id, updatedUser);

        // Retorna o DTO com os dados do usuário atualizado
        return ResponseEntity.ok(
                new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(), user.getRole().name()));
    }

    // Deleta um usuário (somente o próprio usuário ou ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userController.isUserOwnData(authentication, #id)")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id, Authentication authentication) {
        // Verifica se o nome do usuário autenticado corresponde ao nome do usuário na
        // URL
        if (!isUserOwnData(authentication, id.toString())) {
            throw new RuntimeException("Acesso não autorizado");
        }

        // Deleta o usuário do banco de dados
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para verificar se o usuário é o dono dos dados
    public boolean isUserOwnData(Authentication authentication, String username) {
        String usernameAuthenticated = authentication.getName();
        return usernameAuthenticated.equals(username);
    }
}
