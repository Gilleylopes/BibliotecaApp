package br.com.tjrj.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssuntoResponse {
    private Long id;
    private String descricao;
}
