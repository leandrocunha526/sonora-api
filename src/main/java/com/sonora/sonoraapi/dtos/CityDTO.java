package com.sonora.sonoraapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class CityDTO {
    private Integer id;

    @NotBlank(message = "O estado é obrigatório")
    private String state;

    @NotBlank(message = "O nome da cidade é obrigatório")
    @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres")
    private String name;
}
