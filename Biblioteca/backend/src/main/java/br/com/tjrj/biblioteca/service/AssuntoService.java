package br.com.tjrj.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.tjrj.biblioteca.dto.request.AssuntoRequest;
import br.com.tjrj.biblioteca.dto.response.AssuntoResponse;
import br.com.tjrj.biblioteca.entity.Assunto;
import br.com.tjrj.biblioteca.exception.custom.AssuntoJaExisteException;
import br.com.tjrj.biblioteca.exception.custom.AssuntoNaoEncontradoException;
import br.com.tjrj.biblioteca.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    public AssuntoResponse salvar(AssuntoRequest request) {
        String descricaoNormalizada = request.getDescricao().trim();
        if (assuntoRepository.existsByDescricaoIgnoreCase(descricaoNormalizada)) {
            throw new AssuntoJaExisteException(descricaoNormalizada);
        }

        Assunto assunto = Assunto.builder()
                .descricao(request.getDescricao())
                .build();

        assunto = assuntoRepository.save(assunto);

        return new AssuntoResponse(assunto.getId(), assunto.getDescricao());
    }

    public List<AssuntoResponse> listarTodos() {
        return assuntoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public AssuntoResponse buscarPorId(Long id) {
        Assunto assunto = assuntoRepository.findById(id)
                .orElseThrow(() -> new AssuntoNaoEncontradoException(id));
    
        return toResponse(assunto);
    }

    public AssuntoResponse atualizar(Long id, AssuntoRequest request) {
        Assunto assunto = assuntoRepository.findById(id)
                .orElseThrow(() -> new AssuntoNaoEncontradoException(id));

        String descricaoNormalizada = request.getDescricao().trim();
        if (assuntoRepository.existsByDescricaoIgnoreCase(descricaoNormalizada) &&
                !assunto.getDescricao().equalsIgnoreCase(descricaoNormalizada)) {
            throw new AssuntoJaExisteException(descricaoNormalizada);
        }

        assunto.setDescricao(request.getDescricao());
        assunto = assuntoRepository.save(assunto);

        return toResponse(assunto);
    }

    public void excluir(Long id) {
        if (!assuntoRepository.existsById(id)) {
            throw new AssuntoNaoEncontradoException(id);
        }
        assuntoRepository.deleteById(id);
    }

    private AssuntoResponse toResponse(Assunto assunto) {
        return new AssuntoResponse(assunto.getId(), assunto.getDescricao());
    }
}