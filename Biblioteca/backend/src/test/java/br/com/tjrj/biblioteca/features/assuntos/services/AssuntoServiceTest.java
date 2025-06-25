package br.com.tjrj.biblioteca.features.assuntos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tjrj.biblioteca.dto.request.AssuntoRequest;
import br.com.tjrj.biblioteca.entity.Assunto;
import br.com.tjrj.biblioteca.exception.custom.AssuntoJaExisteException;
import br.com.tjrj.biblioteca.repository.AssuntoRepository;
import br.com.tjrj.biblioteca.service.AssuntoService;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Test
    void deveLancarExcecao_SeAssuntoComMesmoNomeJaExistir() {
        var request = new AssuntoRequest("Filosofia");

        when(assuntoRepository.existsByDescricaoIgnoreCase("Filosofia")).thenReturn(true);

        assertThrows(AssuntoJaExisteException.class, () -> assuntoService.salvar(request));
    }

    @Test
    void deveSalvarAssuntoComSucesso() {
        var request = new AssuntoRequest("Ciência Política");
        var assuntoSalvo = new Assunto(1L, "Ciência Política");

        when(assuntoRepository.existsByDescricaoIgnoreCase(request.getDescricao())).thenReturn(false);
        when(assuntoRepository.save(any())).thenReturn(assuntoSalvo);

        var resultado = assuntoService.salvar(request);

        assertNotNull(resultado);
        assertEquals("Ciência Política", resultado.getDescricao());
    }
}

