package br.com.mastersys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                new ErroResponse(
//                        java.time.LocalDate.now(),
//                        HttpStatus.BAD_REQUEST.value(),
//                        "Validação de Dados Falhou",
//                        ex.getBindingResult().getFieldErrors().stream()
//                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                                .toList()
//                )
//        );

        List<String> mensagens = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validação de Dados Falhou",
                mensagens
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);

    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocioException(RegraNegocioException ex) {
        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Regra de Negócio Violada",
                List.of(ex.getMessage())
        );
        return ResponseEntity.badRequest().body(erroResponse);
    }

}