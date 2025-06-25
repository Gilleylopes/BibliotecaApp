package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class EmailJaExisteException extends ApiException {

    private final String email;

    public EmailJaExisteException(String email) {
        super("exception.email.jaExiste");
        this.email = email;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessageKey() {
        return "exception.email.jaExiste";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{email};
    }
}
