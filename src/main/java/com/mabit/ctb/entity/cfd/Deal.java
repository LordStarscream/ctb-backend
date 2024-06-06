package com.mabit.ctb.entity.cfd;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mabit.ctb.entity.Account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "deal")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private LocalDateTime openTime;

    private TradeType type;

    private BigDecimal size;

    private String item;

    private Double price;

    private Long orderNumber;

    private String comment;

    private EntryType entry;

    private BigDecimal commission;

    private BigDecimal swap;

    private BigDecimal profit;
}
