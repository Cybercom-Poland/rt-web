package com.cybercom.framework.vertx.web.core.server.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

public abstract class AbstractHttpServer extends AbstractVerticle {
    private Router router;
    public final static String HTTP_SERVER_GET_EVENT = "HttpServer.get";


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

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void configureRouting() {
        EventBus eb = vertx.eventBus();
        router.get().handler(routingContext -> {
            eb.send(HTTP_SERVER_GET_EVENT, routingContext.request().absoluteURI(), reply -> {
                routingContext.response().end(reply.result().body().toString());
            });
        });
    }

    private void createServer(HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.requestHandler(router::accept);
        httpServer.listen();
    }
}
