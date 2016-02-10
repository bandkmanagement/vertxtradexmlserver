package com.bandkmanagement.vertx;

import java.math.BigDecimal;

public class TradeXmlData {

    private final String id;
    private final BigDecimal price;

    public TradeXmlData(String id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public TradeXmlData(String auctionId) {
        this(auctionId, BigDecimal.ZERO);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TradeXmlData{" +
                "id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}