package br.com.tjrj.biblioteca.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDetalhadoResponse {

    private Long id;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private BigDecimal valor;

    private List<AssuntoDTO> assuntos;
    private List<AutorDTO> autores;

    @Getter
    @Setter
    public static class AssuntoDTO {
        private Long id;
        private String descricao;
    }

    @Getter
    @Setter
    public static class AutorDTO {
        private Long id;
        private String nome;
    }
}
