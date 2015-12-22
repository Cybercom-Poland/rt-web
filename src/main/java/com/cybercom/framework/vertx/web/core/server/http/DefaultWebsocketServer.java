package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;

public class DefaultWebSocketServer extends AbstractServer {
    private final String contextPath;

    public DefaultWebSocketServer(final String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    @Override
    protected void configureServer(final HttpServer httpServer) {
        final Handler<ServerWebSocket> webSocketHandler = HandlerFactory.defaultWebSocketHandler(getVertx(),contextPath);
        httpServer.websocketHandler(webSocketHandler);
    }
}
