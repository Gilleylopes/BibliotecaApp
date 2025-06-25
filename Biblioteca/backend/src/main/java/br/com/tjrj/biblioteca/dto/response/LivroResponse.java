package br.com.tjrj.biblioteca.dto.response;

import java.math.BigDecimal;
import java.util.List;

import br.com.tjrj.biblioteca.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
public class LivroResponse {
    private Long id;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private BigDecimal valor;
    private List<AssuntoResponse> assuntos;
    private List<AutorResponse> autores;

    public LivroResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.editora = livro.getEditora();
        this.edicao = livro.getEdicao();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.valor = livro.getValor();

        this.assuntos = livro.getAssuntos().stream()
            .map(assunto -> new AssuntoResponse(
                assunto.getId(),
                assunto.getDescricao()
            )).toList();

        this.autores = livro.getAutores().stream()
            .map(autor -> new AutorResponse(
                autor.getId(),
                autor.getNome()
            )).toList();
    }
}
