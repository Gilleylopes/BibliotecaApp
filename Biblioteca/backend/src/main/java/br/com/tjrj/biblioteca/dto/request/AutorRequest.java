package br.com.tjrj.biblioteca.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorRequest {

    @JsonCreator
    public AutorRequest(@JsonProperty("nome") String string) {
        this.nome = string;
    }

    @NotBlank(message = "{autor.nome.obrigatorio}")
    @Size(min = 3, max = 80, message = "{autor.nome.tamanho}")
    private String nome;
}
