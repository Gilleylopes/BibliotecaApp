package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class AutorNaoEncontradoException extends ApiException {

    private final Long id;

    public AutorNaoEncontradoException(Long id) {
        super("exception.autor.naoEncontrado");
        this.id = id;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageKey() {
        return "exception.autor.naoEncontrado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}