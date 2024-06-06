package com.mabit.ctb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "location")
public class Location {

    @Id
    @NotNull
    private String name;

    private String information;

    @ManyToOne
    private Account account;

    public Location(String name, Account account, String information) {
        this.name = name;
        this.account = account;
        this.information = information;
    }

    public Location(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
