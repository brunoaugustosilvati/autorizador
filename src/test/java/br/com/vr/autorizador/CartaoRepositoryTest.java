package br.com.vr.autorizador;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.repository.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class CartaoRepositoryTest {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Test
    void testFindCartaoByNumeroCartao() {
        CriarCartaoRequestDTO request = new CriarCartaoRequestDTO("1234567890123456", "1234");
        cartaoRepository.save(new Cartao(request));

        Optional<Cartao> foundCartao = cartaoRepository.findCartaoByNumeroCartao("1234567890123456");

        Assertions.assertTrue(foundCartao.isPresent());
        Assertions.assertEquals("1234567890123456", foundCartao.get().getNumeroCartao());
        Assertions.assertEquals("1234", foundCartao.get().getSenha());
    }

    @Test
    void testFindCartaoByNumeroCartaoNaoExisteCartao() {
        Optional<Cartao> foundCartao = cartaoRepository.findCartaoByNumeroCartao("0000000000000000");

        Assertions.assertFalse(foundCartao.isPresent());
    }
}
