package com.mabit.ctb.entity;

import com.mabit.ctb.types.TradeDirection;
import com.mabit.ctb.types.TransactionType;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    private Location exchange;

    private Double inValue;

    @ManyToOne
    private Currency inCurrency;

    private Double outValue;

    @ManyToOne
    private Currency outCurrency;

    private Double fee;

    @ManyToOne
    private Currency feeCurrency;

    @ManyToOne
    private Trade trade;

    @ManyToOne
    private FiatExchangeRate inFiatExchange;

    @ManyToOne
    private FiatExchangeRate outFiatExchange;

    private LocalDateTime dateTime;

    private String comment;

    private boolean reportCalculated = false;

    public Transaction(Trade trade, TradeDirection direction) {
        this.updateTransaction(trade, direction);
    }

    public void updateTransaction(Trade trade, TradeDirection direction) {
        if (trade != null) {
            this.type = TransactionType.Trade;
            this.exchange = trade.getExchange();
            this.trade = trade;
            if (direction == TradeDirection.Buy) {
                this.inValue = trade.getValue();
                this.inCurrency = trade.getCurrency();
                this.outValue = trade.getBuyValue();
                this.outCurrency = trade.getBuyCurrency();
                this.fee = trade.getBuyFee();
                this.feeCurrency = trade.getBuyCurrency();
                this.dateTime = trade.getTradingTime();
                this.inFiatExchange = trade.getBuyFiatExchange();
            } else {
                this.inValue = trade.getSellValue();
                this.inCurrency = trade.getSellCurrency();
                this.outValue = trade.getValue();
                this.outCurrency = trade.getCurrency();
                this.outFiatExchange = trade.getSellFiatExchange();
                this.fee = trade.getSellFee();
                this.feeCurrency = trade.getSellCurrency();
                this.dateTime = trade.getSellingTime();
            }
        }
    }

    @Override
    public String toString() {
        return "WalletTransaction{" + "id=" + id + ", type=" + type + ", exchange=" + exchange + ", inValue=" + inValue + ", inCurrency=" + inCurrency + ", outValue=" + outValue + ", outCurrency=" + outCurrency + ", fee=" + fee + ", feeCurrency=" + feeCurrency + ", trade=" + trade + ", inFiatExchange=" + inFiatExchange + ", outFiatExchange=" + outFiatExchange + ", dateTime=" + dateTime + ", comment=" + comment + '}';
    }

}
