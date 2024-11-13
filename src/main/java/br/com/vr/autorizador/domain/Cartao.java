package br.com.vr.autorizador.domain;

import br.com.vr.autorizador.dto.CriarCartaoRequestDTO;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Optional;

@Entity(name="cartoes")
@Table(name="cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroCartao;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private BigDecimal saldo;

    public Cartao() {
    }

    public Cartao(CriarCartaoRequestDTO request) {
        this.numeroCartao = request.numeroCartao();
        this.senha = request.senha();
        this.saldo = new BigDecimal(500);
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void autorizarTransacao(BigDecimal valorTransacao) throws SaldoInsuficienteException {
        Optional.of(this.saldo)
                .filter(saldo -> saldo.compareTo(valorTransacao) >= 0)
                .orElseThrow(() -> new SaldoInsuficienteException("SALDO_INSUFICIENTE"));
        this.saldo = this.saldo.subtract(valorTransacao);
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }
}
