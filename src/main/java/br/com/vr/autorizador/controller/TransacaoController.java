package br.com.vr.autorizador.controller;

import br.com.vr.autorizador.dto.FazerTransacaoRequestDTO;
import br.com.vr.autorizador.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    CartaoService service;

    @Operation(summary = "Autorizar transação", description = "Autoriza uma transação com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação autorizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Cartão não encontrado"),
            @ApiResponse(responseCode = "422", description = "Saldo insuficiente"),
            @ApiResponse(responseCode = "422", description = "Senha inválida")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<?> autorizarTransacao(@RequestBody @Valid FazerTransacaoRequestDTO request) throws Throwable {
        service.autorizarTransacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

}
