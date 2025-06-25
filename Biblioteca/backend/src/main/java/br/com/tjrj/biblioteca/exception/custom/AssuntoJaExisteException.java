package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class AssuntoJaExisteException extends ApiException {

    private final String descricao;

    public AssuntoJaExisteException(String descricao) {
        super("exception.assunto.jaExiste");
        this.descricao = descricao;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessageKey() {
        return "exception.assunto.jaExiste";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{descricao};
    }
}