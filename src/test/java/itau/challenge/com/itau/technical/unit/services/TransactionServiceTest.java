package itau.challenge.com.itau.technical.unit.services;

import itau.challenge.com.itau.technical.dto.StatisticsDTO;
import itau.challenge.com.itau.technical.model.Transaction;
import itau.challenge.com.itau.technical.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    @DisplayName("Should add a transaction successfully")
    void addTransaction_shouldSucceed() {
        Transaction transaction = new Transaction(new BigDecimal("100.00"), OffsetDateTime.now());
        transactionService.addTransaction(transaction);
        assertEquals(1, transactionService.getAllTransactions().size());
    }

    @Test
    @DisplayName("Should calculate statistics correctly for transactions in the last 60 seconds")
    void getStatistics_shouldReturnCorrectStatsForRecentTransactions() {
        transactionService.addTransaction(new Transaction(new BigDecimal("10.50"), OffsetDateTime.now().minusSeconds(10)));
        transactionService.addTransaction(new Transaction(new BigDecimal("20.00"), OffsetDateTime.now().minusSeconds(20)));
        transactionService.addTransaction(new Transaction(new BigDecimal("30.50"), OffsetDateTime.now().minusSeconds(30)));

        transactionService.addTransaction(new Transaction(new BigDecimal("1000.00"), OffsetDateTime.now().minusSeconds(70)));

        StatisticsDTO stats = transactionService.getStatistics();

        assertEquals(3, stats.count());
        assertEquals(0, new BigDecimal("61.00").compareTo(stats.sum()));
        assertEquals(0, new BigDecimal("30.50").compareTo(stats.max()));
        assertEquals(0, new BigDecimal("10.50").compareTo(stats.min()));
        assertEquals(0, new BigDecimal("20.33").compareTo(stats.avg()));
    }

    @Test
    @DisplayName("Should return zero statistics when no recent transactions exist")
    void getStatistics_shouldReturnZeroStatsForOldTransactions() {
        transactionService.addTransaction(new Transaction(new BigDecimal("1000.00"), OffsetDateTime.now().minusMinutes(2)));

        StatisticsDTO stats = transactionService.getStatistics();

        assertEquals(0, stats.count());
        assertEquals(0, BigDecimal.ZERO.compareTo(stats.sum()));
    }
}
