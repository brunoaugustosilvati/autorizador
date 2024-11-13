package br.com.vr.autorizador.validation;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.exception.CartaoNaoExisteException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class CartaoNaoExisteStrategy implements ValidationStrategy{
    @Override
    public void validacaoCartao(Optional<Cartao> cartaoOptional, String message, HttpStatus status, String valor) throws CartaoNaoExisteException {
        cartaoOptional.orElseThrow(() -> new CartaoNaoExisteException(message, status));
    }
}
