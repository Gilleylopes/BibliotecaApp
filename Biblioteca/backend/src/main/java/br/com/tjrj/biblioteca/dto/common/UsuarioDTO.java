package br.com.tjrj.biblioteca.dto.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    @NotBlank(message = "{usuario.username.obrigatorio}")
    @Size(min = 4, max = 20, message = "{usuario.username.tamanho}")
    private String username;

    @NotBlank(message = "{usuario.nome.obrigatorio}")
    @Size(min = 3, max = 60, message = "{usuario.nome.tamanho}")
    private String nome;

    @NotBlank(message = "{usuario.email.obrigatorio}")
    @Email(message = "{usuario.email.invalido}")
    private String email;

    @NotBlank(message = "{usuario.senha.obrigatoria}")
    @Size(min = 6, max = 20, message = "{usuario.senha.tamanho}")
    private String senha;
}
