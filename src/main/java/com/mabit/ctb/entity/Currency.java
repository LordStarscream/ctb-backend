package com.mabit.ctb.entity;

import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="currency")
public class Currency {

    @Id
    @NotNull
    private String ticker;
    private String name;

    public Currency(Currency cur){
        this.name = cur.getName();
        this.ticker = cur.getTicker();
    }

     @Override
    public String toString(){
        return this.name + " ("+this.ticker+")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.ticker);
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Currency other = (Currency) obj;
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
