package br.com.vr.autorizador.validation;

import br.com.vr.autorizador.domain.Cartao;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface ValidationStrategy {
    void validacaoCartao(Optional<Cartao> cartaoOptional, String message, HttpStatus status, String valor) throws Throwable;
}
