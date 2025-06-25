package br.com.tjrj.biblioteca.repository;

import br.com.tjrj.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<Autor> findByNomeIgnoreCase(String nome);
}