package br.com.vr.autorizador.dto;

import br.com.vr.autorizador.domain.Cartao;

public record CriarCartaoResponseDTO(
        String senha,
        String numeroCartao) {

    public CriarCartaoResponseDTO(Cartao cartao){
        this(
                cartao.getSenha(),
                cartao.getNumeroCartao()
        );
    }
}
