package com.mabit.ctb.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "TradingImages")
public class TradingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Date imageTime;

    public TradingImage(Long id, String name, String address, Date dateTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageTime = dateTime;
    }

    public TradingImage(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public TradingImage(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
