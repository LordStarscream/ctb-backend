package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "Hold")
public class Hold implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;

    @OneToOne(orphanRemoval=false)
    private Currency inCurrency;

    private LocalDateTime dateTime;

    @OneToOne(orphanRemoval=false)
    private Location location;

    private Double factor;

    public Hold(Double ammount, Currency inCurrency, LocalDateTime dateTime, Location location, Double factor) {
        this.ammount = ammount;
        this.inCurrency = inCurrency;
        this.dateTime = dateTime;
        this.location = location;
        this.factor = factor;
    }
}
