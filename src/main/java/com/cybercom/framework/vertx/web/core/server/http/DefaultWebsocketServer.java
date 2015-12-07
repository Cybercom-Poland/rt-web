package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class DefaultWebsocketServer extends AbstractServer {
    private final String contextPath;

    private Router mainRouter;

    public DefaultWebsocketServer(final String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        mainRouter = Router.router(vertx);
    }

    @Override
    protected void configureServer(final HttpServer httpServer) {
        final Handler<RoutingContext> routingContextHandler = HandlerFactory.defaultWebSocketHandler(getVertx());
        mainRouter.route(contextPath).handler(routingContextHandler);

        httpServer.requestHandler(mainRouter::accept);
    }
}
