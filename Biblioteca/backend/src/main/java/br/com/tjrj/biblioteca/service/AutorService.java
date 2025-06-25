package br.com.tjrj.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.tjrj.biblioteca.dto.request.AutorRequest;
import br.com.tjrj.biblioteca.dto.response.AutorResponse;
import br.com.tjrj.biblioteca.entity.Autor;
import br.com.tjrj.biblioteca.exception.custom.AutorJaExisteException;
import br.com.tjrj.biblioteca.exception.custom.AutorNaoEncontradoException;
import br.com.tjrj.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorResponse salvar(AutorRequest request) {
        String nomeNormalizado = request.getNome().trim();
        if (autorRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new AutorJaExisteException(request.getNome());
        }

        Autor autor = Autor.builder()
                .nome(request.getNome())
                .build();

        autor = autorRepository.save(autor);

        return new AutorResponse(autor.getId(), autor.getNome());
    }

    public List<AutorResponse> listarTodos() {
        return autorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public AutorResponse buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
    
        return toResponse(autor);
    }

    public AutorResponse atualizar(Long id, AutorRequest request) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
        
        String nomeNormalizado = request.getNome().trim();
        if (autorRepository.existsByNomeIgnoreCase(nomeNormalizado) &&
                !autor.getNome().equalsIgnoreCase(nomeNormalizado)) {
            throw new AutorJaExisteException(nomeNormalizado);
        }

        autor.setNome(request.getNome());
        autor = autorRepository.save(autor);

        return toResponse(autor);
    }

    public void excluir(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new AutorNaoEncontradoException(id);
        }
        autorRepository.deleteById(id);
    }

    private AutorResponse toResponse(Autor autor) {
        return new AutorResponse(autor.getId(), autor.getNome());
    }
}