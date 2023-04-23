package com.mabit.ctb.entity;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "FiatExchangeRate")
public class FiatExchangeRate implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(orphanRemoval = false)
    private Currency cryptoCurrency;

    @OneToOne(orphanRemoval = false)
    private Currency fiatCurrency;

    @OneToOne(orphanRemoval = false)
    private Location exchange;

    private Double factor;

    private LocalDateTime date;

    @OneToOne(orphanRemoval = false)
    @Nullable
    private ExchangeRessource ressource;

    public FiatExchangeRate(Currency cryptoCurrency, Currency fiatCurrency, Location exchange, Double factor,
            LocalDateTime date) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        this.exchange = exchange;
        this.factor = factor;
        this.date = date;
    }

    public FiatExchangeRate(Currency cryptoCurrency, Currency fiatCurrency, Location exchange, LocalDateTime date) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        this.exchange = exchange;
        this.date = date;
    }
}
