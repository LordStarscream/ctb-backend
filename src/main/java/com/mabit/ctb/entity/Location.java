package com.mabit.ctb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    private Boolean isExchange;

    private String information;

    public Location(String name, Boolean isExchange, String information) {
        this.name = name;
        this.isExchange = isExchange;
        this.information = information;
    }

    public Location(String name, Boolean isExchange) {
        this.name = name;
        this.isExchange = isExchange;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
