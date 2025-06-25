package br.com.tjrj.biblioteca.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssuntoRequest {

    @JsonCreator
    public AssuntoRequest(@JsonProperty("descricao") String string) {
        this.descricao = string;
    }

    @NotBlank(message = "{assunto.descricao.obrigatoria}")
    @Size(min = 3, max = 100, message = "{assunto.descricao.tamanho}")
    private String descricao;
}
