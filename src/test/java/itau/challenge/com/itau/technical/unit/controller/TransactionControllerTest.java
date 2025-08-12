package itau.challenge.com.itau.technical.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itau.challenge.com.itau.technical.dto.TransactionDTO;
import itau.challenge.com.itau.technical.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService.removeTransactions();
    }

    @Test
    @DisplayName("POST /transacao should return 201 Created for valid transaction")
    void postTransaction_withValidData_shouldReturnCreated() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal("150.75"), OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /transacao should return 422 for non-positive value")
    void postTransaction_withNonPositiveValue_shouldReturnUnprocessableEntity() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal("-10.00"), OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("GET /estatistica should return 200 OK and correct statistics")
    void getStatistics_shouldReturnOkAndCorrectStats() throws Exception {
        TransactionDTO transaction = new TransactionDTO(new BigDecimal("123.45"), OffsetDateTime.now());
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sum").value(123.45))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.avg").value(123.45));
    }

    @Test
    @DisplayName("DELETE /transacao should return 200 OK")
    void deleteTransactions_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }
}