package itau.challenge.com.itau.technical.services;

import itau.challenge.com.itau.technical.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class); //Logs

    Map<String, Transaction> transactions = new ConcurrentHashMap<>(); //Criado com ConcurrentHashMap por ser Thread-Safe e para
    //segregação com ID's únicos.

    public void addTransaction(Transaction transaction){ //Adição de transações
        String idTransaction = UUID.randomUUID().toString();
        transactions.put(idTransaction, transaction);
        logger.debug("Successfully added transaction with ID: {}", idTransaction);
    }
    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }

    public void removeTransactions(){ //Remoção de transações
        transactions.clear();
        logger.debug("Successfully deleted all in-memory transactions");
    }


}
