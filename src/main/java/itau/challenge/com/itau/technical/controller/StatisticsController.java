package itau.challenge.com.itau.technical.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itau.challenge.com.itau.technical.dto.StatisticsDTO;
import itau.challenge.com.itau.technical.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@Tag(name = "Statistics", description = "Endpoints for retrieving transaction statistics")
public class StatisticsController {

    private final TransactionService service;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); //Logs no Controller

    public StatisticsController(TransactionService service) {
        this.service = service;
    }

    //Pré-requisito 3 -> Calculo de estatísticas
    @GetMapping
    @Operation(summary = "Get transaction statistics",
            description = "Calculates and returns statistics (sum, average, max, min, count) for transactions that occurred in the last 60 seconds.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatisticsDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<StatisticsDTO> listStatistics(HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("Initiated GET /estatistica from IP: {}", ipAddress);

        StatisticsDTO stats = service.getStatistics();
        return ResponseEntity.ok(stats);
    }


}
