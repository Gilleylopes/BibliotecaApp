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
@Table(name = "autor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor extends BaseEntity{

    public Autor(Long id, String string) {
         this.setId(id); 
        this.nome = string;
        this.livros = new HashSet<>(); 
    }

    @NotBlank
    private String nome;

    @ManyToMany(mappedBy = "autores")
    @Builder.Default
    private Set<Livro> livros = new HashSet<>();
}
