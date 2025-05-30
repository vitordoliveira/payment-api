package com.vitoroliveira.paymentapi.dto;

import com.vitoroliveira.paymentapi.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    
    private Long id;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String type;
    private String description;
    private String status;
    
    public static TransactionDTO fromEntity(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSourceAccountNumber(transaction.getSourceAccount().getAccountNumber());
        dto.setTargetAccountNumber(transaction.getTargetAccount().getAccountNumber());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setType(transaction.getType().toString());
        dto.setDescription(transaction.getDescription());
        dto.setStatus(transaction.getStatus().toString());
        return dto;
    }
}