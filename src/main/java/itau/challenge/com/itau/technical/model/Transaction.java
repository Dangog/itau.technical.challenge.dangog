package itau.challenge.com.itau.technical.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class Transaction {
    private BigDecimal valor;
    private OffsetDateTime dataHora;

    public Transaction(BigDecimal valor, OffsetDateTime offsetDateTime) {
    }
}

