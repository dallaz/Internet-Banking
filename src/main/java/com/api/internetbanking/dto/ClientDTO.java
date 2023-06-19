package com.api.internetbanking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientDTO {
    private String nome;
    private Boolean planoExclusive;
    private BigDecimal saldo;
    private String numeroConta;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    public ClientDTO() {
    }

    public ClientDTO(String nome, Boolean planoExclusive, BigDecimal saldo, String numeroConta, LocalDate dataNascimento) {
        this.nome = nome;
        this.planoExclusive = planoExclusive;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
        this.dataNascimento = dataNascimento;
    }
}
