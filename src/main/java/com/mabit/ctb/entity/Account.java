package com.mabit.ctb.entity;

import java.util.Locale;
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
 * @author Mario Bittner
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double value;
    @OneToOne(orphanRemoval = false)
    private Currency referenceCurrency;
    private Locale location;

    public Account(Double value, Currency referenceCurrency, Locale location) {
        this.value = value;
        this.referenceCurrency = referenceCurrency;
        this.location = location;
    }

}
