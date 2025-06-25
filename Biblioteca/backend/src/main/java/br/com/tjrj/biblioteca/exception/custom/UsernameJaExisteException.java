package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class UsernameJaExisteException extends ApiException {

    private final String username;

    public UsernameJaExisteException(String username) {
        super("exception.username.jaExiste");
        this.username = username;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessageKey() {
        return "exception.username.jaExiste";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{username};
    }
}
