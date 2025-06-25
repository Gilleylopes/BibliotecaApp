package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class RefreshTokenInvalidoException extends ApiException {

    public RefreshTokenInvalidoException() {
        super("exception.refresh.invalido");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessageKey() {
        return "exception.refresh.invalido";
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
