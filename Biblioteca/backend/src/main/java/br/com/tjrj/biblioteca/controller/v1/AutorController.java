package br.com.tjrj.biblioteca.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tjrj.biblioteca.dto.request.AutorRequest;
import br.com.tjrj.biblioteca.dto.response.AutorResponse;
import br.com.tjrj.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Autores", description = "Endpoints para gerenciamento de autores")
@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @Operation(summary = "Cadastrar novo autor", description = "Cria um novo autor no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Já existe um autor com esse nome"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<AutorResponse> criarAutor(@Valid @RequestBody AutorRequest request) {
        AutorResponse response = autorService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista de todos os autores cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<AutorResponse>> listarAutores() {
        List<AutorResponse> autores = autorService.listarTodos();
        return ResponseEntity.ok(autores);
    }

    @Operation(summary = "Buscar autor por ID", description = "Retorna os detalhes de um autor específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor encontrado"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> buscarPorId(@PathVariable Long id) {
        AutorResponse response = autorService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        AutorResponse response = autorService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir autor", description = "Remove um autor do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAutor(@PathVariable Long id) {
        autorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}