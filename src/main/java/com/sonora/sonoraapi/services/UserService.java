package com.sonora.sonoraapi.services;

import com.sonora.sonoraapi.dtos.UserDTO;
import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setName(updatedUser.getName());
        user.setUsername(updatedUser.getUsername());
        user.setCpf(updatedUser.getCpf());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
