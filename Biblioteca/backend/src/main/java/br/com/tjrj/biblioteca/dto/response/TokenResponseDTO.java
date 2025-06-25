package br.com.tjrj.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String refreshToken;
}
