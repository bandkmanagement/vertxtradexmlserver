package com.bandkmanagement.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class TradeServerDeployVerticle extends AbstractVerticle {
	
    private static final Logger logger = LoggerFactory.getLogger(TradeServerDeployVerticle.class);

	  // Convenience method so you can run it in your IDE
	  public static void main(String[] args) {
	    Runner.runExample(TradeServerDeployVerticle.class);
	  }

	  @Override
	  public void start() throws Exception {        
        // Deploy TradeXmlServiceVerticle instance and wait for it to start
        vertx.deployVerticle(new TradeXmlServiceVerticle(), res -> {
          if (res.succeeded()) {

            String deploymentID = res.result();

            logger.info("TradeXmlServiceVerticle deployed ok, deploymentID = " + deploymentID);
            
          } else {
            res.cause().printStackTrace();
          }
        });
        
        // Deploy ProcessTradeXmlWVerticle instance as a Worker Verticle and wait for it to start
        //
        // This is where the worker verticle would be configured
        // Now it will simply start and then finish
        vertx.deployVerticle(new ProcessTradeXmlWVerticle(),new DeploymentOptions().setWorker(true), res -> {
          if (res.succeeded()) {

            String deploymentID = res.result();

            logger.info("ProcessTradeXmlWVerticle deployed ok, deploymentID = " + deploymentID);
            
          } else {
            res.cause().printStackTrace();
          }
        });
    }
}