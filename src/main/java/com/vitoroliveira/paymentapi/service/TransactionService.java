package com.vitoroliveira.paymentapi.service;

import com.vitoroliveira.paymentapi.dto.TransactionDTO;
import com.vitoroliveira.paymentapi.dto.TransferDTO;
import com.vitoroliveira.paymentapi.model.Account;
import com.vitoroliveira.paymentapi.model.Transaction;
import com.vitoroliveira.paymentapi.repository.AccountRepository;
import com.vitoroliveira.paymentapi.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }
    
    @Transactional
    public TransactionDTO transferMoney(TransferDTO transferDTO) {
        // Buscar contas de origem e destino
        Account sourceAccount = accountRepository.findByAccountNumber(transferDTO.getSourceAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Conta de origem não encontrada"));
        
        Account targetAccount = accountRepository.findByAccountNumber(transferDTO.getTargetAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Conta de destino não encontrada"));
        
        // Verificar se há saldo suficiente
        if (sourceAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência");
        }
        
        // Criar a transação
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setAmount(transferDTO.getAmount());
        transaction.setDescription(transferDTO.getDescription());
        transaction.setType(Transaction.TransactionType.TRANSFER);
        transaction.setStatus(Transaction.TransactionStatus.PENDING);
        
        // Atualizar saldos
        BigDecimal sourceBalance = sourceAccount.getBalance().subtract(transferDTO.getAmount());
        BigDecimal targetBalance = targetAccount.getBalance().add(transferDTO.getAmount());
        
        sourceAccount.setBalance(sourceBalance);
        targetAccount.setBalance(targetBalance);
        
        // Salvar as contas atualizadas
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
        
        // Atualizar status da transação para completada
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        // Salvar a transação
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        return TransactionDTO.fromEntity(savedTransaction);
    }
    
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com ID: " + id));
        
        return TransactionDTO.fromEntity(transaction);
    }
    
    public List<TransactionDTO> getAccountTransactions(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        
        List<Transaction> transactions = transactionRepository.findAllByAccount(account);
        
        return transactions.stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public Page<TransactionDTO> getAccountTransactionsPaged(String accountNumber, Pageable pageable) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        
        Page<Transaction> transactionsPage = transactionRepository.findAllByAccount(account, pageable);
        
        return transactionsPage.map(TransactionDTO::fromEntity);
    }
    
    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        
        return transactions.stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}