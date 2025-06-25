package br.com.tjrj.biblioteca.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.tjrj.biblioteca.dto.request.LivroRequest;
import br.com.tjrj.biblioteca.dto.response.AssuntoResponse;
import br.com.tjrj.biblioteca.dto.response.AutorResponse;
import br.com.tjrj.biblioteca.dto.response.LivroResponse;
import br.com.tjrj.biblioteca.entity.Assunto;
import br.com.tjrj.biblioteca.entity.Autor;
import br.com.tjrj.biblioteca.entity.Livro;
import br.com.tjrj.biblioteca.exception.custom.LivroJaExisteException;
import br.com.tjrj.biblioteca.exception.custom.LivroNaoEncontradoException;
import br.com.tjrj.biblioteca.repository.AssuntoRepository;
import br.com.tjrj.biblioteca.repository.AutorRepository;
import br.com.tjrj.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AssuntoRepository assuntoRepository;
    private final AutorRepository autorRepository;

    public LivroResponse salvar(LivroRequest request) {
        String tituloNormalizado = request.getTitulo().trim();
        if (livroRepository.existsByTituloIgnoreCase(tituloNormalizado)) {
            throw new LivroJaExisteException(tituloNormalizado);
        }

        Set<Assunto> assuntos = validarAssuntos(request.getAssuntosIds());
        Set<Autor> autores = validarAutores(request.getAutoresIds());

        Livro livro = Livro.builder()
                .titulo(request.getTitulo())
                .editora(request.getEditora())
                .edicao(request.getEdicao())
                .anoPublicacao(request.getAnoPublicacao())
                .valor(request.getValor())
                .assuntos(assuntos)
                .autores(autores)
                .build();

        return toResponse(livroRepository.save(livro));
    }

    public LivroResponse atualizar(Long id, LivroRequest request) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));

        String tituloNormalizado = request.getTitulo().trim();
        if (livroRepository.existsByTituloIgnoreCase(tituloNormalizado)
                && !livro.getTitulo().equalsIgnoreCase(tituloNormalizado)) {
            throw new LivroJaExisteException(tituloNormalizado);
        }

        livro.setTitulo(request.getTitulo());
        livro.setEditora(request.getEditora());
        livro.setEdicao(request.getEdicao());
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setValor(request.getValor());

        livro.setAssuntos(validarAssuntos(request.getAssuntosIds()));
        livro.setAutores(validarAutores(request.getAutoresIds()));

        return toResponse(livroRepository.save(livro));
    }

    public List<LivroResponse> listarTodos() {
        return livroRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public LivroResponse buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));

        return toResponse(livro);
    }

    public void excluir(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontradoException(id);
        }
        livroRepository.deleteById(id);
    }

    private Set<Assunto> validarAssuntos(List<Long> ids) {
        List<Assunto> encontrados = assuntoRepository.findAllById(ids);
        if (encontrados.size() != ids.size()) {
            throw new RuntimeException("Alguns IDs de assuntos fornecidos não existem.");
        }
        return new LinkedHashSet<>(encontrados);
    }

    private Set<Autor> validarAutores(List<Long> ids) {
        List<Autor> encontrados = autorRepository.findAllById(ids);
        if (encontrados.size() != ids.size()) {
            throw new RuntimeException("Alguns IDs de autores fornecidos não existem.");
        }
        return new LinkedHashSet<>(encontrados);
    }

    private LivroResponse toResponse(Livro livro) {
        List<AssuntoResponse> assuntos = livro.getAssuntos().stream()
                .map(a -> new AssuntoResponse(a.getId(), a.getDescricao()))
                .toList();

        List<AutorResponse> autores = livro.getAutores().stream()
                .map(a -> new AutorResponse(a.getId(), a.getNome()))
                .toList();

        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getEditora(),
                livro.getEdicao(),
                livro.getAnoPublicacao(),
                livro.getValor(),
                assuntos,
                autores
        );
    }
}
