package com.vitoroliveira.paymentapi.repository;

import com.vitoroliveira.paymentapi.model.Account;
import com.vitoroliveira.paymentapi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Encontra todas as transações de uma conta (enviadas ou recebidas)
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :account OR t.targetAccount = :account")
    List<Transaction> findAllByAccount(Account account);
    
    // Versão paginada da consulta anterior
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :account OR t.targetAccount = :account")
    Page<Transaction> findAllByAccount(Account account, Pageable pageable);
    
    // Encontra transações enviadas de uma conta
    List<Transaction> findBySourceAccount(Account account);
    
    // Encontra transações recebidas em uma conta
    List<Transaction> findByTargetAccount(Account account);
    
    // Encontra transações por período
    List<Transaction> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
}