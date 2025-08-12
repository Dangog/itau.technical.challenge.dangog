package itau.challenge.com.itau.technical.controller;

import itau.challenge.com.itau.technical.dto.StatisticsDTO;
import itau.challenge.com.itau.technical.services.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class StatisticsController {

    private final TransactionService service;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); //Logs no Controller

    public StatisticsController(TransactionService service) {
        this.service = service;
    }

    //Pré-requisito 3 -> Calculo de estatísticas
    @GetMapping
    public ResponseEntity<StatisticsDTO> listStatistics(){
        logger.info("Initiated LIST /estatistica");

        StatisticsDTO stats = service.getStatistics();
        return ResponseEntity.ok(stats);
    }


}
