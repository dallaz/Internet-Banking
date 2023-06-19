package com.api.internetbanking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    private BigDecimal valor;
}
