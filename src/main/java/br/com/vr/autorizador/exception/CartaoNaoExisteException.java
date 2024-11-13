package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

public class CartaoNaoExisteException extends Throwable {

    private final HttpStatus status;

    public CartaoNaoExisteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
