package itau.challenge.com.itau.technical.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itau.challenge.com.itau.technical.dto.TransactionDTO;
import itau.challenge.com.itau.technical.model.Transaction;
import itau.challenge.com.itau.technical.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
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
@Tag(name = "Transactions", description = "Endpoints for managing financial transactions")
public class TransactionController {

    private final TransactionService service;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); //Logs no Controller

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    //Pré-requisito 1 -> Criação de transações
    @PostMapping
    @Operation(summary = "Create a new transaction",
            description = "Registers a new financial transaction. The value must be positive and the date cannot be in the future.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request format (e.g., malformed JSON)",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Business validation failure (e.g., non-positive value or future date)",
                    content = @Content)
    })
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody TransactionDTO entity, HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("Initiated POST /transacao, {}", ipAddress);
        //Requisito: Transacao nao deve ocorre no futuro ou caso menor ou igual a zero
        if (entity.dataHora().isAfter(OffsetDateTime.now()) || entity.valor().compareTo(BigDecimal.ZERO) <= 0){
            return ResponseEntity.unprocessableEntity().build();
        }

        Transaction transaction = new Transaction(entity.valor(), entity.dataHora());

        service.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Pré-requisito 2 -> Remoção de transações
    @DeleteMapping
    @Operation(summary = "Delete all transactions",
            description = "Clears all transactions from the in-memory storage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All transactions successfully deleted",
                    content = @Content)
    })
    public ResponseEntity<Void> removeTransactions(HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("Initiated DELETE /transacao, {}", ipAddress);

        service.removeTransactions();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Bonus (e para depuracao) -> Listagem de todas transações em array, não ta serializado (ainda)
    @GetMapping
    @Operation(summary = "List all transactions",
            description = "Retrieves a list of all in-memory transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of transactions")
    })
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("Initiated GET /transacao, {}", ipAddress);

        List<Transaction> transactionList = service.getAllTransactions();

        List<TransactionDTO> transactionDtos = transactionList.stream()
                .map(transaction -> new TransactionDTO(transaction.getValor(), transaction.getDataHora()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDtos);
    }

}
