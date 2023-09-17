package com.mabit.ctb.entity;

import com.mabit.ctb.types.TransactionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "TransactionImport")
public class TransactionImport  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String exchange;

    private Double inValue;

    private String inCurrency;

    private Double outValue;

    private String outCurrency;

    private Double fee;

    private String feeCurrency;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate inFiatExchange;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate outFiatExchange;

    private LocalDateTime dateTime;

    private String comment;

}
