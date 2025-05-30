package com.vitoroliveira.paymentapi.repository;

import com.vitoroliveira.paymentapi.model.Account;
import com.vitoroliveira.paymentapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    List<Account> findByUser(User user);
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
}