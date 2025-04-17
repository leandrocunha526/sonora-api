package com.sonora.sonoraapi.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 60, message = "O nome de usuário deve ter entre 3 e 60 caracteres")
    private String username;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;
}
