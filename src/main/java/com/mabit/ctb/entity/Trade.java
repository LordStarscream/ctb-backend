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

    @ManyToOne
    private Location exchange;

    @ManyToOne
    private Currency currency;

    private Double value;

    @ManyToOne
    private Currency buyCurrency;

    private Double buyValue;

    @ManyToOne
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

    @ManyToOne
    private FiatExchangeRate buyFiatExchange;

    @ManyToOne
    private FiatExchangeRate sellFiatExchange;

    @ManyToOne
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
