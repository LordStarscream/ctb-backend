package com.mabit.ctb.entity.report;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.Location;
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
public class Hold {

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

}
