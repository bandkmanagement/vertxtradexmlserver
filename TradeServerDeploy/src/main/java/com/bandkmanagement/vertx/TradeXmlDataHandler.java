package com.bandkmanagement.vertx;

import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;

public class TradeXmlDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(TradeXmlDataHandler.class);

    private final TradeServerRepository repository;
    private final TradeXmlDataValidator validator;

    public TradeXmlDataHandler(TradeServerRepository repository, TradeXmlDataValidator validator) {
        this.repository = repository;
        this.validator = validator;
        
        this.repository.save(new TradeXmlData("1"));
        this.repository.save(new TradeXmlData("2"));
    }

    //
    // NOTE: Rewrite to handle xml data instead of parameter
    //
    public void handleGetTradeXmlData(RoutingContext context) {
    	logger.info("Entering handleGetTradeXmlData()");
        String tradeXmlDataId = context.request().getParam("id");
        Optional<TradeXmlData> tradeXmlData = this.repository.getById(tradeXmlDataId);

        if (tradeXmlData.isPresent()) {
            context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(tradeXmlData.get()));
        } else {
            context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(404)
                .end();
        }
    	logger.info("Leaving handleGetTradeXmlData()");
    }


    public void initTradeXmlDataInSharedData(RoutingContext context) {
        String tradeXmlDataId = context.request().getParam("id");

        Optional<TradeXmlData> tradeXmlData = this.repository.getById(tradeXmlDataId);
        if(!tradeXmlData.isPresent()) {
            this.repository.save(new TradeXmlData(tradeXmlDataId));
        }

        context.next();
    }
}