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
    private String name;
    @ManyToOne
    private Currency referenceCurrency;

    private String information;

    private Boolean isCryptoAccount;

    @ManyToOne
    private AccountType type;

    public Account(Currency referenceCurrency, AccountType type) {
        this.referenceCurrency = referenceCurrency;
        this.name = "Crypto Base";
        this.type = type;
        this.isCryptoAccount = true;
    }

    public Account(Currency referenceCurrency, String information,  String name, AccountType type) {
        this.referenceCurrency = referenceCurrency;
        this.information = information;
        this.name = name;
        this.type = type;
        this.isCryptoAccount = false;
    }

}
