package com.vitoroliveira.paymentapi.controller;

import com.vitoroliveira.paymentapi.dto.TransactionDTO;
import com.vitoroliveira.paymentapi.dto.TransferDTO;
import com.vitoroliveira.paymentapi.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações e transferências")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @Operation(summary = "Realiza transferência", description = "Transfere dinheiro entre contas bancárias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transferência realizada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou saldo insuficiente",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                content = @Content)
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@Valid @RequestBody TransferDTO transferDTO) {
        TransactionDTO transaction = transactionService.transferMoney(transferDTO);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Busca transação por ID", description = "Retorna detalhes de uma transação pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transação encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Transação não encontrada",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    @Operation(summary = "Lista transações de uma conta", description = "Retorna todas as transações de uma conta bancária")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transações encontradas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                content = @Content)
    })
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getAccountTransactions(
            @PathVariable String accountNumber) {
        List<TransactionDTO> transactions = transactionService.getAccountTransactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }
    
    @Operation(summary = "Lista transações paginadas", description = "Retorna transações de uma conta com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transações encontradas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                content = @Content)
    })
    @GetMapping("/account/{accountNumber}/paged")
    public ResponseEntity<Page<TransactionDTO>> getAccountTransactionsPaged(
            @PathVariable String accountNumber,
            Pageable pageable) {
        Page<TransactionDTO> transactions = transactionService.getAccountTransactionsPaged(accountNumber, pageable);
        return ResponseEntity.ok(transactions);
    }
    
    @Operation(summary = "Busca por período", description = "Busca transações em um intervalo de datas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transações encontradas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos",
                content = @Content)
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}