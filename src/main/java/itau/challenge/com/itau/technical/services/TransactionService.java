package itau.challenge.com.itau.technical.services;

import itau.challenge.com.itau.technical.dto.StatisticsDTO;
import itau.challenge.com.itau.technical.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class); //Logs

    //Criado com ConcurrentHashMap por ser Thread-Safe e para segregação com ID's únicos.
    Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    //Adição de transações
    public void addTransaction(Transaction transaction){
        String idTransaction = UUID.randomUUID().toString();
        transactions.put(idTransaction, transaction);
        logger.debug("Successfully added transaction with ID: {}", idTransaction);
    }
    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }

    //Remoção de transações
    public void removeTransactions(){
        transactions.clear();
        logger.debug("Successfully deleted all in-memory transactions");
    }

    //Retorno de estatísticas
    public StatisticsDTO getStatistics() {
        OffsetDateTime now = OffsetDateTime.now();

        //Filtro de transações no último minuto e agrega a lista filtrando não nulos
        List<BigDecimal> recentAmounts = transactions.values().stream()
                .filter(transaction ->
                        transaction.getDataHora() != null &&
                                transaction.getDataHora().isAfter(now.minusSeconds(60)))
                .map(Transaction::getValor)
                .toList();

        //Cao sem estatísticas retornando 0, conforme pedido
        if (recentAmounts.isEmpty()) {
            logger.debug("No recent transactions found to generate statistics.");
            return new StatisticsDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L);
        }

        long countTransactions = recentAmounts.size();

        BigDecimal sumStatistics = recentAmounts.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal maxStatistics = recentAmounts.stream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        BigDecimal minStatistics = recentAmounts.stream()
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        //Modo de arredondamento e escala definidas (por estar usando bigdecimal)
        BigDecimal avgStatistics = sumStatistics.divide(new BigDecimal(countTransactions), 2, RoundingMode.HALF_UP);

        logger.debug("Successfully generated statistics for {} transactions.", countTransactions);

        //Retorno com base no DTO calculado com valores individualmente
        return new StatisticsDTO(sumStatistics, avgStatistics, maxStatistics, minStatistics, countTransactions);
    }

    //Além do teste, retorna todas as transações
    public List<Transaction> getAllTransactions() {
        logger.debug("Retrieving all transactions from memory.");

        // Retorna uma lista de todas as transações, ordenadas pela data/hora
        return transactions.values().stream()
                .sorted(Comparator.comparing(Transaction::getDataHora))
                .collect(Collectors.toList());
    }
}
