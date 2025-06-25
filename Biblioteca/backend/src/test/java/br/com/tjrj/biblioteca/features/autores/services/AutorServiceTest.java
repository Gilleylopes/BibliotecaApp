package br.com.tjrj.biblioteca.features.autores.services;

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

import br.com.tjrj.biblioteca.dto.request.AutorRequest;
import br.com.tjrj.biblioteca.entity.Autor;
import br.com.tjrj.biblioteca.exception.custom.AutorJaExisteException;
import br.com.tjrj.biblioteca.repository.AutorRepository;
import br.com.tjrj.biblioteca.service.AutorService;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Test
    void deveLancarExcecao_SeAutorComMesmoNomeJaExistir() {
        var request = new AutorRequest("George Orwell");

        when(autorRepository.existsByNomeIgnoreCase("George Orwell")).thenReturn(true);

        assertThrows(AutorJaExisteException.class, () -> autorService.salvar(request));
    }

    @Test
    void deveSalvarAutorComSucesso() {
        var request = new AutorRequest("Isaac Asimov");
        var autorSalvo = new Autor(1L, "Isaac Asimov");

        when(autorRepository.existsByNomeIgnoreCase(request.getNome())).thenReturn(false);
        when(autorRepository.save(any())).thenReturn(autorSalvo);

        var resultado = autorService.salvar(request);

        assertNotNull(resultado);
        assertEquals("Isaac Asimov", resultado.getNome());
    }
}

