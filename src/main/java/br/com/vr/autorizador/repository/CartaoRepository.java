package br.com.vr.autorizador.repository;


import br.com.vr.autorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findCartaoByNumeroCartao(String numeroCartao);
}
