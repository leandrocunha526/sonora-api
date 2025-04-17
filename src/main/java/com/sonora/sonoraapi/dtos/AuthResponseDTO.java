package com.sonora.sonoraapi.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String message;
}
