package br.com.vr.autorizador.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarCartaoRequestDTO(
        @NotBlank
        String numeroCartao,
        @NotBlank
        String senha) {
}
