package com.bandkmanagement.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;


public class TradeXmlServiceVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(TradeXmlServiceVerticle.class);
    
    public static final String XML_PROCESSOR_KEY="txml.processor.";
    
    private static final String DEFAULT_ROUTE="/eventbus/*";
    private static final String API_SUB_ROUTE="/api";
    private static final int DEFAULT_PORT=8080;
    private static final String DEFAULT_PATH="txml";
    private static final String DEFAULT_ADDRESS_REGEX=DEFAULT_PATH+"\\.[0-9]+";

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route(DEFAULT_ROUTE).handler(eventBusHandler());
        router.mountSubRouter(API_SUB_ROUTE, tradeXmlDataApiRouter());
        router.route().failureHandler(errorHandler());
        router.route().handler(staticHandler());

        vertx.createHttpServer().requestHandler(router::accept).listen(DEFAULT_PORT);
    }

    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddressRegex(DEFAULT_ADDRESS_REGEX));
        return SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                logger.info("Socket created");
            }
            event.complete(true);
        });
    }

    private Router tradeXmlDataApiRouter() {
        TradeServerRepository repository = new TradeServerRepository(vertx.sharedData());
        TradeXmlDataValidator validator = new TradeXmlDataValidator(repository);
        TradeXmlDataHandler handler = new TradeXmlDataHandler(repository, validator);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route().consumes("application/json");
        router.route().produces("application/json");

        router.route("/"+DEFAULT_PATH+"/:id").handler(handler::initTradeXmlDataInSharedData);
        router.get("/"+DEFAULT_PATH+"/:id").handler(handler::handleGetTradeXmlData);

        return router;
    }

    private ErrorHandler errorHandler() {
        return ErrorHandler.create(true);
    }

    private StaticHandler staticHandler() {
        return StaticHandler.create().setCachingEnabled(false);
    }
}
