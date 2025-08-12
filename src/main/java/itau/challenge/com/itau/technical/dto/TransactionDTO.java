package itau.challenge.com.itau.technical.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionDTO(
        @NotNull(message = "Campo valor não pode ser nulo.")
        BigDecimal valor,

        @NotNull(message = "Campo dataHora não pode ser nulo.")
        OffsetDateTime dataHora
) {
}