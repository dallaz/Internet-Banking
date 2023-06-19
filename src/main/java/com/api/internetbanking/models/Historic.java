package com.api.internetbanking.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Historic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String numeroConta;

    @Column
    private BigDecimal saldoAnterior;

    @Column
    private BigDecimal saldoAtual;

    @Column
    private String taxa;

    @Column
    private String tipoTransacao;

    @Column
    private BigDecimal valor;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate data;

}
