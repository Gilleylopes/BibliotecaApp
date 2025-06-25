package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class AssuntoNaoEncontradoException extends ApiException {

    private final Long id;

    public AssuntoNaoEncontradoException(Long id) {
        super("exception.assunto.naoEncontrado");
        this.id = id;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageKey() {
        return "exception.assunto.naoEncontrado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}