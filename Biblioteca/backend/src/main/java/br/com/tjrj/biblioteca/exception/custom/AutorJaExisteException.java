package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class AutorJaExisteException extends ApiException {

    private final String nome;

    public AutorJaExisteException(String nome) {
        super("exception.autor.jaExiste");
        this.nome = nome;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessageKey() {
        return "exception.autor.jaExiste";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{nome};
    }
}