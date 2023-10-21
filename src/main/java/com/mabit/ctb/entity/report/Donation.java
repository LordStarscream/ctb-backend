package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
import com.mabit.ctb.types.TransactionType;

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
@Table(name = "Income")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;

    @OneToOne(orphanRemoval = false)
    private Currency currency;

    private LocalDateTime outDateTime;

    private String CostBaseCalculation;

    private Double CostBase;

    @OneToOne(orphanRemoval = false)
    private Location outAt;

    private TransactionType type;

    // Wert bei eingang in EUR
    private Double worthAtOut;

    public Donation(Double ammount, Currency currency, LocalDateTime outDateTime, String CostBaseCalculation, Double CostBase, Location outAt, TransactionType type, Double worthAtOut) {
        this.ammount = ammount;
        this.currency = currency;
        this.outDateTime = outDateTime;
        this.CostBaseCalculation = CostBaseCalculation;
        this.CostBase = CostBase;
        this.outAt = outAt;
        this.type = type;
        this.worthAtOut = worthAtOut;
    }

}