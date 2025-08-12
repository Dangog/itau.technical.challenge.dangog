package itau.challenge.com.itau.technical.dto;

import java.math.BigDecimal;

public record StatisticsDTO(
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal max,
        BigDecimal min,
        long count
) {}