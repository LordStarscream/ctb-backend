package com.mabit.ctb.entity;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
import com.mabit.ctb.types.TradingStatus;
import com.mabit.ctb.types.TradingType;
import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Trades")
public class Trade {

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

    public LocalDateTime getTradingTime() {
        return buyingTime;
    }

    public void setTradingTime(LocalDateTime tradingTime) {
        this.buyingTime = tradingTime;
    }
}
