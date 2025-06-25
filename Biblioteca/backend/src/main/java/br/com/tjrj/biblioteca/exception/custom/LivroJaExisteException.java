package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class LivroJaExisteException extends ApiException {

    private final String titulo;

    public LivroJaExisteException(String titulo) {
        super("exception.livro.jaExiste");
        this.titulo = titulo;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessageKey() {
        return "exception.livro.jaExiste";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{titulo};
    }
}