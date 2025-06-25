package br.com.tjrj.biblioteca.security;

import br.com.tjrj.biblioteca.entity.Usuario;
import br.com.tjrj.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha())
                .authorities(
                        usuario.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getNome()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
