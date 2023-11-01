package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.types.TransactionType;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;

    @ManyToOne
    private Currency currency;

    private LocalDateTime inDateTime;

    @ManyToOne
    private Location inAt;

    private TransactionType type;

    private String info;

    // Wert bei eingang in EUR
    private Double worthAtIncome;

    @ManyToOne
    private Report report;

    public Income(Double ammount, Currency currency, LocalDateTime inDateTime, Location inAt, TransactionType type, String info, Double worthAtIncome, Report report) {
        this.ammount = ammount;
        this.currency = currency;
        this.inDateTime = inDateTime;
        this.inAt = inAt;
        this.type = type;
        this.info = info;
        this.worthAtIncome = worthAtIncome;
        this.report = report;
    }

}
