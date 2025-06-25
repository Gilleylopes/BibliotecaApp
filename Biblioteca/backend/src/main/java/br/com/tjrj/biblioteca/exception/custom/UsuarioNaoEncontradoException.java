package br.com.tjrj.biblioteca.exception.custom;

import org.springframework.http.HttpStatus;

import br.com.tjrj.biblioteca.exception.base.ApiException;

public class UsuarioNaoEncontradoException extends ApiException {

    private final String identificador;

    public UsuarioNaoEncontradoException(String identificador) {
        super("exception.usuario.naoEncontrado");
        this.identificador = identificador;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageKey() {
        return "exception.usuario.naoEncontrado";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{identificador};
    }
}
