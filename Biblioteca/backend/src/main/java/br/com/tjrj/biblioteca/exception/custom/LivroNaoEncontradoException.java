package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class LivroNaoEncontradoException extends ApiException {

    private final Long id;

    public LivroNaoEncontradoException(Long id) {
        super("exception.livro.naoEncontrado");
        this.id = id;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageKey() {
        return "exception.livro.naoEncontrado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}