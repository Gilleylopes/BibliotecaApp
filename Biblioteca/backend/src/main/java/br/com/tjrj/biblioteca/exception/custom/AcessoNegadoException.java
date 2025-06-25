package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class AcessoNegadoException extends ApiException {

    public AcessoNegadoException() {
        super("exception.acesso.negado"); 
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessageKey() {
        return "exception.acesso.negado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
