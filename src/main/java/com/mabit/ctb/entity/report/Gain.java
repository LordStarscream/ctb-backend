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
@Table(name = "Gain")
public class Gain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ammount;

    @ManyToOne
    private Currency currency;

    private LocalDateTime inDateTime;

    private LocalDateTime outDateTime;

    private String shortLong;

    @ManyToOne
    private Location buyAt;

    @ManyToOne
    private Location sellAt;

    // Erlös
    private Double proceeds;

    private Double costbasis;

    private Double profit;

    public Gain(Double ammount, Currency currency, LocalDateTime inDateTime, LocalDateTime outDateTime, String shortLong, Location buyAt, Location sellAt, Double proceeds, Double costbasis, Double profit) {
        this.ammount = ammount;
        this.currency = currency;
        this.inDateTime = inDateTime;
        this.outDateTime = outDateTime;
        this.shortLong = shortLong;
        this.buyAt = buyAt;
        this.sellAt = sellAt;
        this.proceeds = proceeds;
        this.costbasis = costbasis;
        this.profit = profit;
    }

}
