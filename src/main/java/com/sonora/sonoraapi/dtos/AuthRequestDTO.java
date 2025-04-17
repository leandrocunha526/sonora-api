package com.sonora.sonoraapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 60, message = "O nome de usuário deve ter entre 3 e 60 caracteres")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;
}
