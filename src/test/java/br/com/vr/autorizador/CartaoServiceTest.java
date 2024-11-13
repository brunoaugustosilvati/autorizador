package br.com.vr.autorizador;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
import br.com.vr.autorizador.dto.FazerTransacaoRequestDTO;
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.service.CartaoService;
import br.com.vr.autorizador.validation.CartaoExisteStrategy;
import br.com.vr.autorizador.validation.CartaoNaoExisteStrategy;
import br.com.vr.autorizador.validation.CartaoSenhaInvalidaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCartao() throws Throwable {
        CriarCartaoRequestDTO requestDTO = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestDTO);
        CriarCartaoResponseDTO expectedResponse = new CriarCartaoResponseDTO(cartao);

        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.empty());
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        CriarCartaoResponseDTO response = cartaoService.criarCartao(requestDTO);

        assertEquals(expectedResponse, response);
    }

    @Test
    void criarCartaoJaExiste() throws Throwable {
        CriarCartaoRequestDTO requestDTO = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestDTO);
        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));

        assertThrows(CartaoJaExisteException.class, () -> cartaoService.criarCartao(requestDTO));
    }

    @Test
    void obterSaldo() throws Throwable {
        CriarCartaoRequestDTO requestDTO = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestDTO);
        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));

        // Act
        BigDecimal saldo = cartaoService.obterSaldo("1234567890123456");

        // Assert
        assertEquals(BigDecimal.valueOf(500), saldo);
    }

    @Test
    void obterSaldoCartaoNaoExiste() throws Throwable {
        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.empty());

        assertThrows(CartaoNaoExisteException.class, () -> cartaoService.obterSaldo("1234567890123456"));
    }

    @Test
    void autorizarTransacao() throws Throwable {
        CriarCartaoRequestDTO requestCriar = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestCriar);
        FazerTransacaoRequestDTO requestDTO = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(100));

        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        cartaoService.autorizarTransacao(requestDTO);

        assertEquals(BigDecimal.valueOf(400), cartao.getSaldo());
    }

    @Test
    void autorizarTransacaoCartaoNaoExiste() throws Throwable {
        FazerTransacaoRequestDTO requestDTO = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(100));

        when(cartaoRepository.findCartaoByNumeroCartao(requestDTO.numeroCartao())).thenReturn(Optional.empty());

        assertThrows(CartaoNaoExisteException.class, () -> cartaoService.autorizarTransacao(requestDTO));
    }


    @Test
    void autorizarTransacaoSenhaInvalida() throws Throwable {
        CriarCartaoRequestDTO requestCriar = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestCriar);
        FazerTransacaoRequestDTO requestDTO = new FazerTransacaoRequestDTO("1234567890123456", "1235", BigDecimal.valueOf(100));

        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));

        assertThrows(SenhaInvalidaException.class, () -> cartaoService.autorizarTransacao(requestDTO));
    }

    @Test
    void autorizarTransacaoSaldoInsuficiente() throws Throwable {
        CriarCartaoRequestDTO requestCriar = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(requestCriar);
        FazerTransacaoRequestDTO requestDTO = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(1000));

        when(cartaoRepository.findCartaoByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));

        assertThrows(SaldoInsuficienteException.class, () -> cartaoService.autorizarTransacao(requestDTO));
    }
}
