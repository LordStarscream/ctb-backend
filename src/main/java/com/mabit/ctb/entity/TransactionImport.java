package com.mabit.ctb.entity;

import com.mabit.ctb.types.TradeDirection;
import com.mabit.ctb.types.TransactionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "TransactionImport")
public class TransactionImport implements Serializable {

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


    public TransactionImport(TransactionType type, String exchange, Double inValue, String inCurrency, Double outValue, String outCurrency, Double fee, String feeCurrency, FiatExchangeRate inFiatExchange, FiatExchangeRate outFiatExchange, LocalDateTime dateTime, String comment) {
        this.type = type;
        this.exchange = exchange;
        this.inValue = inValue;
        this.inCurrency = inCurrency;
        this.outValue = outValue;
        this.outCurrency = outCurrency;
        this.fee = fee;
        this.feeCurrency = feeCurrency;
        this.inFiatExchange = inFiatExchange;
        this.outFiatExchange = outFiatExchange;
        this.dateTime = dateTime;
        this.comment = comment;
    }
}
