package com.mabit.ctb.entity;

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

    private String information;

    public Account(Currency referenceCurrency) {
        this.referenceCurrency = referenceCurrency;
    }

    public Account(Currency referenceCurrency, String information) {
        this.referenceCurrency = referenceCurrency;
        this.information = information;
    }

}
