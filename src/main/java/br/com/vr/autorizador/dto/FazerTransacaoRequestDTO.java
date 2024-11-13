package br.com.vr.autorizador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FazerTransacaoRequestDTO(
        @NotBlank
        String numeroCartao,
        @NotBlank
        String senhaCartao,
        @NotNull
        BigDecimal valor) {
}
