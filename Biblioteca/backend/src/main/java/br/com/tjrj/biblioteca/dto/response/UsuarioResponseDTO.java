package br.com.tjrj.biblioteca.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.com.tjrj.biblioteca.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String nome;
    private String email;
    private List<String> papeis;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papeis = usuario.getRoles().stream()
        .map(role -> role.getNome())
        .collect(Collectors.toList());
    }
}
