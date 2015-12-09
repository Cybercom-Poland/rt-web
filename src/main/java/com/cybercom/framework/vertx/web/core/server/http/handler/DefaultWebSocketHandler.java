package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;


final public class DefaultWebSocketHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultWebSocketHandler.class);
    private final SockJSHandler sockJSHandler;
    private final Serializer serializer;
    private final EventBus eventBus;

    public DefaultWebSocketHandler(Vertx vertx) {
        SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);

        this.serializer = new DefaultSerializer();
        sockJSHandler = SockJSHandler.create(vertx, options);
        eventBus = vertx.eventBus();

        sockJSHandler.socketHandler(sockJSSocketHandler -> {
            sockJSSocketHandler.handler(buffer -> {
                JsonObject jsonRequest = new JsonObject(buffer.toString());
                try {
                    final Request request = serializer.deserialize(jsonRequest, Request.class);
                    eventBus.send(request.getAddress(), jsonRequest, HandlerFactory.defaultWebSocketResponseHandler(sockJSSocketHandler));
                } catch (SerializerException e) {
                    LOG.error("Can not deserialize request", e);
                }
            });
        });
    }

    @Override
    public void handle(RoutingContext event) {
        sockJSHandler.handle(event);
    }
}
