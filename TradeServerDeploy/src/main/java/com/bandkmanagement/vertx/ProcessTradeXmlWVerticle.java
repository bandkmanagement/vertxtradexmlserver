package com.bandkmanagement.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ProcessTradeXmlWVerticle extends AbstractVerticle {
	
    private static final Logger logger = LoggerFactory.getLogger(ProcessTradeXmlWVerticle.class);

	@Override
	public void start() {
		final EventBus eventBus = vertx.eventBus();

		// Eventbus will contain xml to be processed in the XML_PROCESSOR_KEY
		
		// Process incoming xml requests
		// 1) Parse
		// 4) Send trade server response to AcknowledgeTradeXmlVehicle
		logger.info("[Worker] Starting in " + Thread.currentThread().getName());
  
	    eventBus.consumer(TradeXmlServiceVerticle.XML_PROCESSOR_KEY, message -> {
	      logger.info("[Worker] Consuming data in " + Thread.currentThread().getName());
	      String xml = (String) message.body();
	      message.reply(processTradeXml(xml));
	    });
	    logger.info("[Worker] Finished");
	}
	
	private String processTradeXml(final String fixXml)
	{
		final String ACK_STRING="ACK STRING DATA";
		try {
			// 1) Send to trade server
			// 2) Get trade server response
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ACK_STRING;
	}
}
