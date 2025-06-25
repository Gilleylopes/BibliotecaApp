package br.com.tjrj.biblioteca.entity;
import java.util.HashSet;
import java.util.Set;

import br.com.tjrj.biblioteca.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "assunto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assunto extends BaseEntity {

    public Assunto(Long id, String string) {
         this.setId(id); 
        this.descricao = string;
        this.livros = new HashSet<>(); 
    }

    @NotBlank
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    @Builder.Default
    private Set<Livro> livros = new HashSet<>();
}
