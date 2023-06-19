package com.api.internetbanking.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = {"numeroConta"}))
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private Boolean planoExclusive;

    @Column
    private BigDecimal saldo;

    @Column
    private String numeroConta;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataNascimento;
}
