package br.com.tjrj.biblioteca.controller.v1;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tjrj.biblioteca.dto.common.LoginDTO;
import br.com.tjrj.biblioteca.dto.common.UsuarioDTO;
import br.com.tjrj.biblioteca.dto.response.AuthResponseDTO;
import br.com.tjrj.biblioteca.dto.response.UsuarioResponseDTO;
import br.com.tjrj.biblioteca.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Autenticação", description = "Endpoints de login, cadastro, refresh e perfil")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário com perfil padrão ROLE_USER."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro com sucesso"),
        @ApiResponse(responseCode = "409", description = "E-mail ou username já em uso")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Dados do novo usuário",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Registro válido",
                value = """
                {
                "username": "gilley",
                "nome": "Gilley Lopes",
                "email": "gilley@exemplo.com",
                "senha": "gilley123"
                }
                """
            )
        )
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioResponseDTO response = authService.registrarUsuario(dto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Autenticar usuário",
        description = "Recebe credenciais válidas e retorna um token JWT com refresh token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Credenciais de acesso",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Login com sucesso",
                value = """
                {
                "username": "gilley",
                "senha": "123456"
                }
                """
            )
        )
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> autenticar(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO response = authService.autenticarUsuario(loginDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Renovar access token",
        description = "Gera um novo JWT válido a partir de um refresh token válido ainda não expirado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Novo access token gerado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Corpo contendo apenas o refresh token",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de requisição de refresh token",
                value = """
                {
                "refreshToken": "eae999f6-9bb3-48f4-ade4-3bd58d1cb86c"
                }
                """
            )
        )
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        var response = authService.renovarToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Buscar perfil do usuário autenticado",
        description = "Retorna informações básicas do usuário logado com base no token JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário autenticado identificado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/me")
    public ResponseEntity<?> usuarioLogado(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(authService.obterUsuarioLogado(user));
    }
}
