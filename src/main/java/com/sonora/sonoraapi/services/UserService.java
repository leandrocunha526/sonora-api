package com.sonora.sonoraapi.services;

import com.sonora.sonoraapi.dtos.UserDTO;
import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(),
                        user.getRole().name()))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getCpf(), user.getRole().name());
    }

    public User updateUser(Integer id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualizando dados do usuário
        user.setName(updatedUser.getName());
        user.setUsername(updatedUser.getUsername());
        user.setCpf(updatedUser.getCpf());
        user.setRole(updatedUser.getRole());

        // Se a senha for diferente da original, criptografá-la
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
