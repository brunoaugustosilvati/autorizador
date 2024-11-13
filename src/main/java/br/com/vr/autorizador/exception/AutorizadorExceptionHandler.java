package br.com.vr.autorizador.exception;

import br.com.vr.autorizador.dto.CriarCartaoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AutorizadorExceptionHandler {

    @ExceptionHandler(CartaoJaExisteException.class)
    public ResponseEntity<CriarCartaoResponseDTO> handleCartaoJaExiste(CartaoJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getCriarCartaoResponseDTO());
    }

    @ExceptionHandler(CartaoNaoExisteException.class)
    public ResponseEntity<?> handleCartaoNaoExiste(CartaoNaoExisteException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<?> handleSenhaInvalida(SenhaInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
