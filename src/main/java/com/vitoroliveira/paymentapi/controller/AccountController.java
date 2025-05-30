package com.vitoroliveira.paymentapi.controller;

import com.vitoroliveira.paymentapi.dto.AccountDTO;
import com.vitoroliveira.paymentapi.model.Account;
import com.vitoroliveira.paymentapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Contas", description = "Endpoints para gerenciamento de contas bancárias")
public class AccountController {
    
    private final AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @Operation(summary = "Cria uma nova conta", description = "Cria uma nova conta para um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "CHECKING") String accountType) {
        
        Account.AccountType type = Account.AccountType.valueOf(accountType);
        AccountDTO createdAccount = accountService.createAccount(userId, type);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Lista contas de um usuário", description = "Retorna todas as contas de um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contas encontradas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@PathVariable Long userId) {
        List<AccountDTO> accounts = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
    
    @Operation(summary = "Busca conta por número", description = "Retorna detalhes de uma conta pelo seu número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                content = @Content)
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
        AccountDTO account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }
}