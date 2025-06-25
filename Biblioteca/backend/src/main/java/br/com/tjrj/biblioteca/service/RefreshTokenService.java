package br.com.tjrj.biblioteca.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.tjrj.biblioteca.entity.RefreshToken;
import br.com.tjrj.biblioteca.entity.Usuario;
import br.com.tjrj.biblioteca.exception.custom.RefreshTokenInvalidoException;
import br.com.tjrj.biblioteca.exception.custom.TokenExpiradoException;
import br.com.tjrj.biblioteca.exception.custom.UsuarioNaoEncontradoException;
import br.com.tjrj.biblioteca.repository.RefreshTokenRepository;
import br.com.tjrj.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsuarioRepository usuarioRepository;

    @Value("${app.jwt.refresh-expiracao}")
    private Long tempoExpiracaoMillis;

    public RefreshToken criarToken(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("ID: " + usuarioId));

        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiraEm(LocalDateTime.now().plusNanos(tempoExpiracaoMillis * 1_000_000))                .usuario(usuario)
                .revogado(false)
                .build();

        return refreshTokenRepository.save(token);
    }

    public RefreshToken validarRefreshToken(String token) {
        RefreshToken rt = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenInvalidoException());

        if (rt.isRevogado() || rt.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new TokenExpiradoException();
        }

        return rt;
    }

    public void revogarTokensDoUsuario(Usuario usuario) {
        refreshTokenRepository.findAllByUsuario(usuario)
                .forEach(token -> {
                    token.setRevogado(true);
                    refreshTokenRepository.save(token);
                });
    }
}
