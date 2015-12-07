package com.cybercom.framework.vertx.web.core.server.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;


public class DefaultWebSocketHandler implements Handler<RoutingContext> {
    private SockJSHandler sockJSHandler;

    public DefaultWebSocketHandler(Vertx vertx) {
        SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);
        sockJSHandler = SockJSHandler.create(vertx, options);

        sockJSHandler.socketHandler(sockJSSocket -> {
            // Just echo the data back
            //TODO this method should send message to eventbus
            sockJSSocket.handler(sockJSSocket::write);
        });
    }

    @Override
    public void handle(RoutingContext event) {
        sockJSHandler.handle(event);
    }
}
