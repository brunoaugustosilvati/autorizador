package br.com.vr.autorizador.service;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
import br.com.vr.autorizador.dto.FazerTransacaoRequestDTO;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.validation.CartaoExisteStrategy;
import br.com.vr.autorizador.validation.CartaoNaoExisteStrategy;
import br.com.vr.autorizador.validation.CartaoSenhaInvalidaStrategy;
import br.com.vr.autorizador.validation.ValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class CartaoService {

    private final Map<String, ValidationStrategy> mapStrategy = Map.of(
        "CARTAO_EXISTE", new CartaoExisteStrategy(),
        "CARTAO_INEXISTENTE", new CartaoNaoExisteStrategy(),
        "SENHA_INVALIDA", new CartaoSenhaInvalidaStrategy()
    );

    @Autowired
    private CartaoRepository repository;

    public CriarCartaoResponseDTO criarCartao(CriarCartaoRequestDTO request) throws Throwable {
        var cartaoOptional = repository.findCartaoByNumeroCartao(request.numeroCartao());
        mapStrategy.get("CARTAO_EXISTE").validacaoCartao(cartaoOptional, "", null, "");
        var cartao = repository.save(new Cartao(request));
        return new CriarCartaoResponseDTO(cartao);
    }

    public BigDecimal obterSaldo(String numeroCartao) throws Throwable {
        var cartaoOptional = repository.findCartaoByNumeroCartao(numeroCartao);
        mapStrategy.get("CARTAO_INEXISTENTE").validacaoCartao(cartaoOptional, "", HttpStatus.NOT_FOUND, "");
        return cartaoOptional.get().getSaldo();
    }

    public void autorizarTransacao(FazerTransacaoRequestDTO request) throws Throwable {

        var cartaoOptional = repository.findCartaoByNumeroCartao(request.numeroCartao());

        mapStrategy.get("CARTAO_INEXISTENTE").validacaoCartao(cartaoOptional, "CARTAO_INEXISTENTE", HttpStatus.UNPROCESSABLE_ENTITY, "");
        mapStrategy.get("SENHA_INVALIDA").validacaoCartao(cartaoOptional, "SENHA_INVALIDA", HttpStatus.UNPROCESSABLE_ENTITY, request.senhaCartao());

        var cartao = cartaoOptional.get();
        cartao.autorizarTransacao(request.valor());

        repository.save(cartao);
    }
}
