package itau.challenge.com.itau.technical.controller;

import itau.challenge.com.itau.technical.dto.TransactionDTO;
import itau.challenge.com.itau.technical.model.Transaction;
import itau.challenge.com.itau.technical.services.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService service;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); //Logs no Controller

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    //Pré-requisito 1 -> Criação de transações
    @PostMapping
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody TransactionDTO entity){
        logger.info("Initiated POST /transacao");
        //Requisito: Transacao nao deve ocorre no futuro ou caso menor ou igual a zero
        if (entity.dataHora().isAfter(OffsetDateTime.now()) || entity.valor().compareTo(BigDecimal.ZERO) <= 0){
            return ResponseEntity.unprocessableEntity().build();
        }

        logger.debug("BBBBBBBBBBBBBBBBBBB: {}",entity.toString());
        Transaction transaction = new Transaction(entity.valor(), entity.dataHora());
        logger.debug(String.valueOf(entity.dataHora()));
        logger.debug(String.valueOf(entity.valor()));


        service.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Pré-requisito 2 -> Remoção de transações
    @DeleteMapping
    public ResponseEntity<Void> removeTransactions(){
        logger.info("Initiated DELETE /transacao");
        service.removeTransactions();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Bonus (e para depuracao):-> Listagem de todas transações
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        logger.info("Initiated GET /transacao to list all transactions");

        // 1. Chama o novo método do serviço
        List<Transaction> transactionList = service.getAllTransactions();

        // 2. Converte a lista de Model (`Transaction`) para uma lista de DTO (`TransactionDTO`)
        //    Isso é uma boa prática para não expor seu modelo interno na API.
        List<TransactionDTO> transactionDtos = transactionList.stream()
                .map(transaction -> new TransactionDTO(transaction.getValor(), transaction.getDataHora()))
                .collect(Collectors.toList());

        // 3. Retorna a lista de DTOs no corpo da resposta com status 200 OK
        return ResponseEntity.ok(transactionDtos);
    }

}
