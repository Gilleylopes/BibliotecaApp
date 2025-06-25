package br.com.tjrj.biblioteca.features.livros.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tjrj.biblioteca.dto.request.LivroRequest;
import br.com.tjrj.biblioteca.dto.response.LivroResponse;
import br.com.tjrj.biblioteca.entity.Assunto;
import br.com.tjrj.biblioteca.entity.Autor;
import br.com.tjrj.biblioteca.entity.Livro;
import br.com.tjrj.biblioteca.repository.AssuntoRepository;
import br.com.tjrj.biblioteca.repository.AutorRepository;
import br.com.tjrj.biblioteca.repository.LivroRepository;
import br.com.tjrj.biblioteca.service.LivroService;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AssuntoRepository assuntoRepository;

    private LivroRequest request;
    private Livro livro;

    @BeforeEach
    void setup() {
        request = new LivroRequest();
        request.setTitulo("1984");
        request.setEditora("Companhia das Letras");
        request.setEdicao(1);
        request.setAnoPublicacao("1949");
        request.setValor(BigDecimal.valueOf(49.9));
        request.setAutoresIds(List.of(1L));
        request.setAssuntosIds(List.of(2L));

        Autor autor = new Autor(1L, "George Orwell");
        Assunto assunto = new Assunto(2L, "Distopia");

        livro = new Livro();
        livro.setId(10L);
        livro.setTitulo("1984");
        livro.setEditora("Companhia das Letras");
        livro.setEdicao(1);
        livro.setAnoPublicacao("1949");
        livro.setValor(BigDecimal.valueOf(49.9));
        livro.setAutores(Set.of(autor));
        livro.setAssuntos(Set.of(assunto));
    }

    @Test
    void deveCadastrarLivroComSucesso() {
        when(autorRepository.findAllById(request.getAutoresIds()))
            .thenReturn(List.of(new Autor(1L, "George Orwell")));

        when(assuntoRepository.findAllById(request.getAssuntosIds()))
            .thenReturn(List.of(new Assunto(2L, "Distopia")));

        when(livroRepository.save(any(Livro.class)))
            .thenReturn(livro);

        LivroResponse response = livroService.salvar(request);

        assertThat(response).isNotNull();
        assertThat(response.getTitulo()).isEqualTo("1984");
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    void deveLancarExcecao_SeAutorNaoExistir() {
        when(autorRepository.findAllById(request.getAutoresIds()))
            .thenReturn(List.of()); // vazio

        assertThatThrownBy(() -> livroService.salvar(request))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Autor");

        verify(livroRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecao_SeAssuntoNaoExistir() {
        when(autorRepository.findAllById(request.getAutoresIds()))
            .thenReturn(List.of(new Autor(1L, "George Orwell")));

        when(assuntoRepository.findAllById(request.getAssuntosIds()))
            .thenReturn(List.of()); // vazio

        assertThatThrownBy(() -> livroService.salvar(request))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Assunto");
    }
}
