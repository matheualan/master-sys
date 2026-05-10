package br.com.mastersys.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        LocalDateTime data,
        Integer status,
        String erro,
        List<String> mensagens
) {
}
