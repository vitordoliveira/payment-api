package com.vitoroliveira.paymentapi.service;

import com.vitoroliveira.paymentapi.dto.AccountDTO;
import com.vitoroliveira.paymentapi.model.Account;
import com.vitoroliveira.paymentapi.model.User;
import com.vitoroliveira.paymentapi.repository.AccountRepository;
import com.vitoroliveira.paymentapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public AccountDTO createAccount(Long userId, Account.AccountType accountType) {
        // Buscar o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));
        
        // Gerar número de conta único
        String accountNumber = generateUniqueAccountNumber();
        
        // Criar nova conta
        Account account = new Account();
        account.setUser(user);
        account.setType(accountType);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO); // Saldo inicial zero
        
        // Salvar a conta
        Account savedAccount = accountRepository.save(account);
        
        return AccountDTO.fromEntity(savedAccount);
    }
    
    public List<AccountDTO> getUserAccounts(Long userId) {
        // Buscar o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));
        
        // Buscar as contas do usuário
        List<Account> accounts = accountRepository.findByUser(user);
        
        // Converter para DTOs
        return accounts.stream()
                .map(AccountDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public AccountDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada com número: " + accountNumber));
        
        return AccountDTO.fromEntity(account);
    }
    
    @Transactional
    public AccountDTO updateBalance(String accountNumber, BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo");
        }
        
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada com número: " + accountNumber));
        
        account.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        
        return AccountDTO.fromEntity(updatedAccount);
    }
    
    // Método auxiliar para gerar número de conta único
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            // Gerar número de 10 dígitos
            accountNumber = String.format("%010d", random.nextInt(1000000000));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        
        return accountNumber;
    }
}