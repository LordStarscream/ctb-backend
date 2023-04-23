package com.mabit.ctb.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "exchangeRessource")
public class ExchangeRessource implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(orphanRemoval = false)
    private Location exchange;

    private String ressource;

    private LocalDateTime date;

    public ExchangeRessource(Location exchange, String ressource, LocalDateTime date) {
        this.exchange = exchange;
        this.ressource = ressource;
        this.date = date;
    }

}
