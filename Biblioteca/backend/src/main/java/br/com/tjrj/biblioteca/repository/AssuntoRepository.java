package br.com.tjrj.biblioteca.repository;

import br.com.tjrj.biblioteca.entity.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {

    boolean existsByDescricaoIgnoreCase(String descricao);

    Optional<Assunto> findByDescricaoIgnoreCase(String descricao);
}