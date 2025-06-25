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

import br.com.tjrj.biblioteca.dto.request.AssuntoRequest;
import br.com.tjrj.biblioteca.dto.response.AssuntoResponse;
import br.com.tjrj.biblioteca.service.AssuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Assuntos", description = "Endpoints para gerenciamento de assuntos")
@RestController
@RequestMapping("/api/v1/assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService assuntoService;

    @Operation(summary = "Cadastrar novo assunto", description = "Cria um novo assunto no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Assunto criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Já existe um assunto com essa descrição"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<AssuntoResponse> criarAssunto(@Valid @RequestBody AssuntoRequest request) {
        AssuntoResponse response = assuntoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os assuntos", description = "Retorna uma lista de todos os assuntos cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de assuntos retornada com sucesso")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<AssuntoResponse>> listarAssuntos() {
        List<AssuntoResponse> assuntos = assuntoService.listarTodos();
        return ResponseEntity.ok(assuntos);
    }

    @Operation(summary = "Buscar assunto por ID", description = "Retorna os detalhes de um assunto específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Assunto encontrado"),
        @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AssuntoResponse> buscarPorId(@PathVariable Long id) {
        AssuntoResponse response = assuntoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar assunto", description = "Atualiza os dados de um assunto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Assunto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<AssuntoResponse> atualizarAssunto(@PathVariable Long id, @Valid @RequestBody AssuntoRequest request) {
        AssuntoResponse response = assuntoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir assunto", description = "Remove um assunto do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Assunto excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAssunto(@PathVariable Long id) {
        assuntoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}