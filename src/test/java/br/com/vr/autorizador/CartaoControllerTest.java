package br.com.vr.autorizador;

import br.com.vr.autorizador.controller.CartaoController;
import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoExisteException;
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

class CartaoControllerTest {

    @InjectMocks
    private CartaoController cartaoController;

    @Mock
    private CartaoService cartaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarCartao() throws Throwable {
        CriarCartaoRequestDTO request = new CriarCartaoRequestDTO("1234567890123456", "1234");
        CriarCartaoResponseDTO responseDTO = new CriarCartaoResponseDTO("1234567890123456", "1234");

        when(cartaoService.criarCartao(request)).thenReturn(responseDTO);

        ResponseEntity<CriarCartaoResponseDTO> response = cartaoController.criarCartao(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testCriarCartaoJaExiste() throws Throwable {
        CriarCartaoRequestDTO request = new CriarCartaoRequestDTO("1234567890123456", "1234");
        Cartao cartao = new Cartao(request);
        when(cartaoService.criarCartao(request)).thenThrow(new CartaoJaExisteException(new CriarCartaoResponseDTO(cartao)));

        assertThrows(CartaoJaExisteException.class, () -> cartaoController.criarCartao(request));
    }

    @Test
    void testObterSaldo() throws Throwable {
        String numeroCartao = "1234567890123456";
        BigDecimal saldo = BigDecimal.valueOf(500);

        when(cartaoService.obterSaldo(numeroCartao)).thenReturn(saldo);

        ResponseEntity<BigDecimal> response = cartaoController.obterSaldo(numeroCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

    @Test
    void testObterSaldoCartaoNaoExiste() throws Throwable {
        String numeroCartao = "1234567890123456";

        when(cartaoService.obterSaldo(numeroCartao)).thenThrow(new CartaoNaoExisteException("", HttpStatus.NOT_FOUND));

        assertThrows(CartaoNaoExisteException.class, () -> cartaoController.obterSaldo(numeroCartao));
    }
}

