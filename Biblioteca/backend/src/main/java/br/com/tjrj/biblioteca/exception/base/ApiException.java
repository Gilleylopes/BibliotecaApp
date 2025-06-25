package br.com.tjrj.biblioteca.exception.base;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
    public abstract String getMessageKey();
    public abstract Object[] getArgs();
}
