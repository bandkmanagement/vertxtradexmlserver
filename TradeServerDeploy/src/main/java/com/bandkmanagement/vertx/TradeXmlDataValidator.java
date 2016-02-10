package com.bandkmanagement.vertx;

public class TradeXmlDataValidator {

    private final TradeServerRepository repository;

    public TradeXmlDataValidator(TradeServerRepository repository) {
        this.repository = repository;
    }

    //
    // NOTE: Add code to validate the tradeXmlData that is passed in
    //
    public boolean validate(TradeXmlData tradeXmlData) {
    	TradeXmlData tradeServerDatabase = repository.getById(tradeXmlData.getId())
            .orElseThrow(() -> new TradeXmlDataNotFoundException(tradeXmlData.getId()));

        return tradeServerDatabase.getPrice().compareTo(tradeXmlData.getPrice()) == -1;
    }
}
