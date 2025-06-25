package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class SenhaInvalidaException extends ApiException {

    public SenhaInvalidaException() {
        super("exception.senha.invalida");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessageKey() {
        return "exception.senha.invalida";
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
