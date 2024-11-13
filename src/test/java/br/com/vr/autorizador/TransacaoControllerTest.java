package br.com.vr.autorizador;

import br.com.vr.autorizador.controller.TransacaoController;
import br.com.vr.autorizador.dto.FazerTransacaoRequestDTO;
import br.com.vr.autorizador.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private CartaoService cartaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAutorizarTransacao() throws Throwable {
        FazerTransacaoRequestDTO request = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(100));

        ResponseEntity<?> response = transacaoController.autorizarTransacao(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("OK", response.getBody());
        verify(cartaoService, times(1)).autorizarTransacao(request);
    }

    @Test
    void testAutorizarTransacaoCartaoNaoExiste() throws Throwable {
        FazerTransacaoRequestDTO request = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(100));

        doThrow(new CartaoNaoExisteException("CARTAO_INEXISTENTE",HttpStatus.UNPROCESSABLE_ENTITY)).when(cartaoService).autorizarTransacao(request);

        assertThrows(CartaoNaoExisteException.class, () -> transacaoController.autorizarTransacao(request));
        verify(cartaoService, times(1)).autorizarTransacao(request);
    }

    @Test
    void testAutorizarTransacaoSaldoInsuficiente() throws Throwable {
        FazerTransacaoRequestDTO request = new FazerTransacaoRequestDTO("1234567890123456", "1234", BigDecimal.valueOf(1000));

        doThrow(new SaldoInsuficienteException("SALDO_INSUFICIENTE")).when(cartaoService).autorizarTransacao(request);

        assertThrows(SaldoInsuficienteException.class, () -> transacaoController.autorizarTransacao(request));
        verify(cartaoService, times(1)).autorizarTransacao(request);
    }

    @Test
    void testAutorizarTransacaoSenhaInvalida() throws Throwable {
        FazerTransacaoRequestDTO request = new FazerTransacaoRequestDTO("1234567890123456", "1111", BigDecimal.valueOf(100));

        doThrow(new SenhaInvalidaException("SENHA_INVALIDA")).when(cartaoService).autorizarTransacao(request);

        assertThrows(SenhaInvalidaException.class, () -> transacaoController.autorizarTransacao(request));
        verify(cartaoService, times(1)).autorizarTransacao(request);
    }
}
