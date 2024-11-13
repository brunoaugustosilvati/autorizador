package br.com.vr.autorizador.validation;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class CartaoSenhaInvalidaStrategy implements ValidationStrategy {
    @Override
    public void validacaoCartao(Optional<Cartao> cartaoOptional, String message, HttpStatus status, String valor) throws Throwable {
        cartaoOptional
                .filter(cartao -> cartao.getSenha().equals(valor))
                .orElseThrow(() -> new SenhaInvalidaException("SENHA_INVALIDA"));
    }
}
