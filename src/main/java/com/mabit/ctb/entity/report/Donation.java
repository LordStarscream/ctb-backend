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

    @ManyToOne
    private Currency currency;

    private LocalDateTime outDateTime;

    private String costBaseCalculation;

    private Double costBase;

    @ManyToOne
    private Location outAt;

    private TransactionType type;

    // Wert bei eingang in EUR
    private Double worthAtOut;

    @ManyToOne
    private Report report;

    public Donation(Double ammount, Currency currency, LocalDateTime outDateTime, String costBaseCalculation, Double costBase, Location outAt, TransactionType type, Double worthAtOut, Report report) {
        this.ammount = ammount;
        this.currency = currency;
        this.outDateTime = outDateTime;
        this.costBaseCalculation = costBaseCalculation;
        this.costBase = costBase;
        this.outAt = outAt;
        this.type = type;
        this.worthAtOut = worthAtOut;
        this.report = report;
    }

}