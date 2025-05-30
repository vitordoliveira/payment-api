package com.vitoroliveira.paymentapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    
    @NotBlank(message = "Conta de origem é obrigatória")
    private String sourceAccountNumber;
    
    @NotBlank(message = "Conta de destino é obrigatória")
    private String targetAccountNumber;
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;
    
    private String description;
}