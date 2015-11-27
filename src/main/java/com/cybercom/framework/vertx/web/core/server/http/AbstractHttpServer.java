package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractHttpServer extends AbstractVerticle {
    private static final String ROOT_PATH = "/*";

    private Router router;

    public AbstractHttpServer() {
        this.router = Router.router(vertx);
    }

    @Override
    public void start() throws Exception {
        final HttpServerOptions httpServerOptions = defaultHttpServerOptions();
        configureRouting();
        configureServer(httpServerOptions);
        createServer(httpServerOptions);
    }

    private HttpServerOptions defaultHttpServerOptions() {
        return new HttpServerOptions();
    }

    private void configureRouting() {
        final EventBus eventBus = vertx.eventBus();

        final Handler<RoutingContext> getHandler = HandlerFactory.defaultGetHandler(eventBus);
        router.get(ROOT_PATH).handler(getHandler);
    }

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void createServer(final HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.requestHandler(router::accept);
        httpServer.listen();
    }
}
