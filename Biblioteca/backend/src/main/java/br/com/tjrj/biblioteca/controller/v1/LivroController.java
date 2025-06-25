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

import br.com.tjrj.biblioteca.dto.request.LivroRequest;
import br.com.tjrj.biblioteca.dto.response.LivroResponse;
import br.com.tjrj.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros")
@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @Operation(summary = "Cadastrar novo livro", description = "Cria um novo livro no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<LivroResponse> criarLivro(@Valid @RequestBody LivroRequest request) {
        LivroResponse response = livroService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista de todos os livros cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<LivroResponse>> listarLivros() {
        List<LivroResponse> livros = livroService.listarTodos();
        return ResponseEntity.ok(livros);
    }

    @Operation(summary = "Buscar livro por ID", description = "Retorna os detalhes de um livro específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        LivroResponse response = livroService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar livro", description = "Atualiza os dados de um livro existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        LivroResponse response = livroService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir livro", description = "Remove um livro do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}