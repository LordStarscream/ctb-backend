package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
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
@Table(name = "Deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private Double availableAmmount;

    private boolean hasAvailableAmmount;

    @ManyToOne
    private Currency currency;

    private LocalDateTime dateTime;

    @ManyToOne
    private Location location;

    private Double factor;

    public Deposit(Double value, Currency currency, LocalDateTime dateTime, Location location, Double factor) {
        this.value = value;
        this.availableAmmount = value;
        this.hasAvailableAmmount = (value > 0);
        this.currency = currency;
        this.dateTime = dateTime;
        this.location = location;
        this.factor = factor;
    }

}
