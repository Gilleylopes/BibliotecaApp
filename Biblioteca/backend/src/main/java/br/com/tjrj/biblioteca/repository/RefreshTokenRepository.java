package br.com.tjrj.biblioteca.repository;

import br.com.tjrj.biblioteca.entity.RefreshToken;
import br.com.tjrj.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUsuario(Usuario usuario);

    void deleteAllByUsuario(Usuario usuario);
}
