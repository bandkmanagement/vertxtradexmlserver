package com.bandkmanagement.vertx;

import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

import java.math.BigDecimal;
import java.util.Optional;

public class TradeServerRepository {

    private SharedData sharedData;

    public TradeServerRepository(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public Optional<TradeXmlData> getById(String tradeXmlDataId) {
        LocalMap<String, String> tradeXmlSharedData = this.sharedData.getLocalMap(tradeXmlDataId);
        return Optional.of(tradeXmlSharedData)
            .filter(m -> !m.isEmpty())
            .map(this::convertToTradeXmlData);
    }

    public void save(TradeXmlData tradeXmlData) {
        LocalMap<String, String> tradeServerSharedData = this.sharedData.getLocalMap(tradeXmlData.getId());

        tradeServerSharedData.put("id", tradeXmlData.getId());
        tradeServerSharedData.put("price", tradeXmlData.getPrice().toString());
    }

    private TradeXmlData convertToTradeXmlData(LocalMap<String, String> tradeXmlData) {
        return new TradeXmlData(
        	tradeXmlData.get("id"),
            new BigDecimal(tradeXmlData.get("price"))
        );
    }
}
