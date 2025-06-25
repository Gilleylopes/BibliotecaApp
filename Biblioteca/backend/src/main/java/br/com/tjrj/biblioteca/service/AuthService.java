package br.com.tjrj.biblioteca.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tjrj.biblioteca.dto.common.LoginDTO;
import br.com.tjrj.biblioteca.dto.common.UsuarioDTO;
import br.com.tjrj.biblioteca.dto.response.AuthResponseDTO;
import br.com.tjrj.biblioteca.dto.response.UsuarioResponseDTO;
import br.com.tjrj.biblioteca.entity.Usuario;
import br.com.tjrj.biblioteca.exception.custom.EmailJaExisteException;
import br.com.tjrj.biblioteca.exception.custom.SenhaInvalidaException;
import br.com.tjrj.biblioteca.exception.custom.UsernameJaExisteException;
import br.com.tjrj.biblioteca.exception.custom.UsuarioNaoEncontradoException;
import br.com.tjrj.biblioteca.repository.RoleRepository;
import br.com.tjrj.biblioteca.repository.UsuarioRepository;
import br.com.tjrj.biblioteca.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;


    public UsuarioResponseDTO registrarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameJaExisteException(dto.getUsername());
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaExisteException(dto.getEmail());
        }

        var roleUser = roleRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role padrão não encontrada"));

        Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha())) 
                .roles(Set.of(roleUser))
                .build();

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

    public AuthResponseDTO autenticarUsuario(LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getSenha()
                )
            );
            if (!auth.isAuthenticated()) {
                throw new SenhaInvalidaException();
            }

            Usuario usuario = usuarioRepository.findByUsername(loginDTO.getUsername())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(loginDTO.getUsername()));

            List<String> roles = usuario.getRoles().stream().map(r -> r.getNome()).toList();
            String jwt = jwtTokenProvider.gerarToken(usuario.getUsername(), roles);
            String refresh = refreshTokenService.criarToken(usuario.getId()).getToken();

            return new AuthResponseDTO(jwt, refresh);

        } catch (BadCredentialsException e) {
            throw new SenhaInvalidaException();
        }
    }

    public AuthResponseDTO renovarToken(String refreshToken) {
        var tokenValido = refreshTokenService.validarRefreshToken(refreshToken);

        Usuario usuario = tokenValido.getUsuario();
        List<String> roles = usuario.getRoles().stream().map(r -> r.getNome()).toList();
        String novoJwt = jwtTokenProvider.gerarToken(usuario.getUsername(), roles);

        return new AuthResponseDTO(novoJwt, refreshToken);
    }

    public Map<String, String> obterUsuarioLogado(UserDetails user) {
        return Map.of("username", user.getUsername());
    }

    public static UsuarioResponseDTO toResponse(Usuario usuario) {
    return UsuarioResponseDTO.builder()
        .id(usuario.getId())
        .username(usuario.getUsername())
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .papeis(usuario.getRoles().stream()
            .map(role -> role.getNome())
            .collect(Collectors.toList()))
        .build();
    }
}