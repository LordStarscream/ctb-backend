package com.mabit.ctb.entity;

import java.util.Locale;
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
    @ManyToOne
    private Currency referenceCurrency;

    public Account(Currency referenceCurrency) {
        this.referenceCurrency = referenceCurrency;
    }

}
