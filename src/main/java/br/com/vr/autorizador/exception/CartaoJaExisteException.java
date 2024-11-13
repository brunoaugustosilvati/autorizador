package br.com.vr.autorizador.exception;

import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;

public class CartaoJaExisteException extends Throwable {

    private final CriarCartaoResponseDTO criarCartaoResponseDTO;

    public CartaoJaExisteException(CriarCartaoResponseDTO response) {
        super();
        this.criarCartaoResponseDTO = response;
    }

    public CriarCartaoResponseDTO getCriarCartaoResponseDTO() {
        return criarCartaoResponseDTO;
    }
}
