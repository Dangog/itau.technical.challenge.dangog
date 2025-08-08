package itau.challenge.com.itau.technical.controller;

import itau.challenge.com.itau.technical.dto.TransactionDTO;
import itau.challenge.com.itau.technical.model.Transaction;
import itau.challenge.com.itau.technical.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody TransactionDTO entity){
        if (entity.dataHora().isAfter(OffsetDateTime.now())){ //Requisito: Transacao nao deve ocorre no futuro
            return ResponseEntity.unprocessableEntity().build();
        }

        service.addTransaction(new Transaction(entity.valor(),entity.dataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
