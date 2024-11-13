package br.com.vr.autorizador.validation;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class CartaoExisteStrategy implements ValidationStrategy{

    @Override
    public void validacaoCartao(Optional<Cartao> cartaoOptional, String message, HttpStatus status, String valor) throws CartaoJaExisteException {
        if (cartaoOptional.isPresent()) {
            throw new CartaoJaExisteException(new CriarCartaoResponseDTO(cartaoOptional.get()));
        }
    }
}
