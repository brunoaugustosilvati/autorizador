package br.com.vr.autorizador.controller;

import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
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

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoService service;

    @Operation(summary = "Criar um novo cartão", description = "Cria um novo cartão com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Cartão já existe")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<CriarCartaoResponseDTO> criarCartao(@RequestBody @Valid CriarCartaoRequestDTO request) throws Throwable {
        var cartao = service.criarCartao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
    }

    @Operation(summary = "Obter saldo do cartão", description = "Retorna o saldo do cartão com o número de cartão fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) throws Throwable {
        return ResponseEntity.ok(service.obterSaldo(numeroCartao));
    }
}
