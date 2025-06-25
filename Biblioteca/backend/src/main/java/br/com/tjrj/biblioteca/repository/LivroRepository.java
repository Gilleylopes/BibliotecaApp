package br.com.tjrj.biblioteca.repository;

import br.com.tjrj.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    Optional<Livro> findByTituloIgnoreCase(String titulo);

}