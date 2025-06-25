package br.com.tjrj.biblioteca.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroRequest {

    @NotBlank(message = "{livro.titulo.obrigatorio}")
    @Size(min = 2, max = 150, message = "{livro.titulo.tamanho}")
    private String titulo;

    @Size(max = 40, message = "{livro.editora.tamanho}")
    private String editora;

    @Min(value = 1, message = "{livro.edicao.minimo}")
    @Max(value = 6, message = "{livro.edicao.maximo}")
    private Integer edicao;

    @Size(max = 4, message = "{livro.anoPublicacao.tamanho}")
    private String anoPublicacao;

    @NotNull(message = "{livro.valor.obrigatorio}")
    @DecimalMin(value = "0.01", inclusive = false, message = "{livro.valor.minimo}")
    private BigDecimal valor;

    @NotNull(message = "{livro.assuntoId.obrigatorio}")
    private List<Long> assuntosIds;

    @NotEmpty(message = "{livro.autoresIds.obrigatorio}")
    private List<Long> autoresIds;
}