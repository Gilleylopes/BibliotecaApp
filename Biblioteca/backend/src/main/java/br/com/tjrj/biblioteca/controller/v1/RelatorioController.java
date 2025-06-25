package br.com.tjrj.biblioteca.controller.v1;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tjrj.biblioteca.service.relatorios.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios da biblioteca")
@RestController
@RequestMapping("/api/v1/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Operation(
        summary = "Relatório de livros por autor",
        description = "Gera um relatório em PDF contendo livros agrupados por autor e seus respectivos assuntos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao gerar o relatório")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "/livros", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorioLivrosPorAutor() {
        byte[] pdf = relatorioService.gerarRelatorioLivros();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=livros_por_autor.pdf")
            .body(pdf);
    }
}

