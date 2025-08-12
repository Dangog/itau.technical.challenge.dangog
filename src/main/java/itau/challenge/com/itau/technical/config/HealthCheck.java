package itau.challenge.com.itau.technical.config;

import itau.challenge.com.itau.technical.services.TransactionService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    private final TransactionService transactionService;

    public HealthCheck(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Health health() {
        if (transactionService == null) {
            // Se o serviço não foi injetado, algo está muito errado.
            return Health.down().withDetail("service", "TransactionService is not available").build();
        }
        return Health.up().withDetail("service", "TransactionService is available").build();
    }
}
