package com.bandkmanagement.vertx;

public class TradeXmlDataNotFoundException extends RuntimeException {
    public TradeXmlDataNotFoundException(String tradeXmlDataId) {
        super("TradeXmlData not found: " + tradeXmlDataId);
    }
}
