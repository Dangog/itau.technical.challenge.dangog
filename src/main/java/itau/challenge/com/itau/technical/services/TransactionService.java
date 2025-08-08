package itau.challenge.com.itau.technical.services;

import itau.challenge.com.itau.technical.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TransactionService {

    Map<String, Transaction> transactions = new ConcurrentHashMap<>(); //Criado com ConcurrentHashMap por ser Thread-Safe e para
    //segregação com ID's únicos.

    public void addTransaction(Transaction transaction){
        String idTransaction = UUID.randomUUID().toString();
        transactions.put(idTransaction, transaction);
    }
    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }


}
