package br.com.tjrj.biblioteca.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "{login.username.obrigatorio}")
    private String username;

    @NotBlank(message = "{login.senha.obrigatoria}")
    private String senha;
}
