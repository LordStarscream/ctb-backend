package com.mabit.ctb.entity;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
import com.mabit.ctb.types.TradingStatus;
import com.mabit.ctb.types.TradingType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Trades")
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = false)
    private Location exchange;

    @OneToOne(orphanRemoval = false)
    private Currency currency;

    private Double value;

    @OneToOne(orphanRemoval = false)
    private Currency buyCurrency;

    private Double buyValue;

    @OneToOne(orphanRemoval = false)
    private Currency sellCurrency;

    private Double sellValue;

    // @Column(nullable = true)
    private Double buyFee;

    // @Column(nullable = true)
    private Double sellFee;

    private LocalDateTime buyingTime;

    private LocalDateTime sellingTime;

    @Enumerated(EnumType.STRING)
    private TradingType tradingType;

    @Enumerated(EnumType.STRING)
    private TradingStatus status;

    // @Column(nullable = true)
    private Double stopLoss;

    private String comment;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate buyFiatExchange;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate sellFiatExchange;

    @OneToOne(orphanRemoval = false)
    private FiatExchangeRate tradeFiatExchange;

    @OneToMany(orphanRemoval = true)
    private Set<TradingImage> images;

    public Trade(Location exchange, Currency currency, Double value, Currency buyCurrency, Double buyValue,
            Currency sellCurrency, Double sellValue, Double buyFee, Double sellFee, LocalDateTime tradingTime,
            LocalDateTime sellingTime, TradingType tradingType, TradingStatus status, Double stopLoss, String comment,
            Set<TradingImage> images) {
        this.exchange = exchange;
        this.currency = currency;
        this.value = value;
        this.buyCurrency = buyCurrency;
        this.buyValue = buyValue;
        this.sellCurrency = sellCurrency;
        this.sellValue = sellValue;
        this.buyFee = buyFee;
        this.sellFee = sellFee;
        this.buyingTime = tradingTime;
        this.sellingTime = sellingTime;
        this.tradingType = tradingType;
        this.status = status;
        this.stopLoss = stopLoss;
        this.comment = comment;
        this.images = images;
    }

    public LocalDateTime getTradingTime() {
        return buyingTime;
    }

    public void setTradingTime(LocalDateTime tradingTime) {
        this.buyingTime = tradingTime;
    }
}
