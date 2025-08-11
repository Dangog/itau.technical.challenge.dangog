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

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService service;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); //Logs no Controller

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping //Pré-requisito 1 -> Criação de transações
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody TransactionDTO entity){
        logger.info("Initiated POST /transacao");
        if (entity.dataHora().isAfter(OffsetDateTime.now())){ //Requisito: Transacao nao deve ocorre no futuro
            return ResponseEntity.unprocessableEntity().build();
        }

        service.addTransaction(new Transaction(entity.valor(),entity.dataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping //Pré-requisito 2 -> Remoção de transações
    public ResponseEntity<Void> removeTransactions(){
        logger.info("Initiated DELETE /transacao");
        service.removeTransactions();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
