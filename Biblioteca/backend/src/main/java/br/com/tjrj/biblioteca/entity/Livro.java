package br.com.tjrj.biblioteca.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.tjrj.biblioteca.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro extends BaseEntity {

    @NotBlank
    @Size(max = 255)
    @Column(length = 255, nullable = false, unique = true)
    private String titulo;

    @Size(max = 40)
    @Column(length = 40)
    private String editora;

    @Column
    private Integer edicao;

    @Size(max = 4)
    @Column(length = 4)
    private String anoPublicacao;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToMany
    @JoinTable(
        name = "livro_assunto",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "assunto_id")
    )
    @Builder.Default
    private Set<Assunto> assuntos = new HashSet<>();


    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @Builder.Default
    private Set<Autor> autores = new HashSet<>();
}
