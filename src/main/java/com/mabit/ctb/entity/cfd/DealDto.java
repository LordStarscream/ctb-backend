package com.mabit.ctb.entity.cfd;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealDto {

    private Long id;

    private Long accountId;

    private String openTime;

    private String type;

    private BigDecimal size;

    private String item;

    private Double price;

    private Long orderNumber;

    private String comment;

    private String entry;

    private BigDecimal commission;

    private BigDecimal swap;

    private BigDecimal profit;

    public DealDto(Deal deal){
        this.id = deal.getId();
        this.accountId = deal.getAccount().getId();
        this.type = deal.getType().toString();
        this.size = deal.getSize();
        this.item = deal.getItem();
        this.price = deal.getPrice();
        this.orderNumber = deal.getOrderNumber();
        this.comment = deal.getComment();
        this.entry = deal.getEntry().toString();
        this.commission = deal.getCommission();
        this.swap = deal.getSwap();
        this.profit = deal.getProfit();
        this.openTime = deal.getOpenTime().format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
