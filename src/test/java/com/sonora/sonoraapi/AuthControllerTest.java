package com.sonora.sonoraapi;

import com.sonora.sonoraapi.entities.User;
import com.sonora.sonoraapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() throws Exception {
        // Criptografando a senha antes de salvar
        String encodedPassword = passwordEncoder.encode("123456");

        User user = new User();
        user.setName("Test User");
        user.setUsername("usuario");
        user.setCpf("12345678901");
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Dados de entrada para o registro
        String userJson = """
                    {
                      "name": "Test User",
                      "username": "usuario",  // Nome de usuário único para o teste
                      "cpf": "12345678901",
                      "password": "123456",  // A senha será criptografada no backend
                    }
                """;
    }
}
