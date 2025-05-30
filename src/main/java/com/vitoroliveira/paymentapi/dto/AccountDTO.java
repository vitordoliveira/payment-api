package com.vitoroliveira.paymentapi.dto;

import com.vitoroliveira.paymentapi.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Long userId;
    private LocalDateTime createdAt;
    
    public static AccountDTO fromEntity(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getType().toString());
        dto.setBalance(account.getBalance());
        dto.setUserId(account.getUser().getId());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }
}