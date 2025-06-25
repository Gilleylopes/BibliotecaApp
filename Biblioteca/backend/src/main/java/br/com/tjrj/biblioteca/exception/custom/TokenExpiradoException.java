package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class TokenExpiradoException extends ApiException {

    public TokenExpiradoException() {
        super("exception.token.expirado");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessageKey() {
        return "exception.token.expirado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
