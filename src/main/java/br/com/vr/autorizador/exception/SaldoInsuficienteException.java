package br.com.vr.autorizador.exception;

public class SaldoInsuficienteException extends Throwable {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
