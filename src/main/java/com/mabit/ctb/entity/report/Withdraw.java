package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "Withdraw")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;

    private boolean isFee;

    @ManyToOne
    private Currency currency;

    private LocalDateTime dateTime;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Report report;

    @ManyToMany
    private List<Deposit> fromDeposits;

    public Withdraw(Double ammount, Currency currency, LocalDateTime dateTime, Location location, Report report, List<Deposit> deposits) {
        this(ammount,currency,dateTime,location,report,deposits,false);
    }

    public Withdraw(Double ammount, Currency currency, LocalDateTime dateTime, Location location, Report report, List<Deposit> deposits, boolean isFee) {
        this.ammount = ammount;
        this.currency = currency;
        this.dateTime = dateTime;
        this.location = location;
        this.report = report;
        this.fromDeposits = deposits;
        this.isFee = isFee;
    }

}
