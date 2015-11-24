package com.cybercom.framework.vertx.web.core.server.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;

public abstract class AbstractHttpServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        final HttpServerOptions httpServerOptions = defaultHttpServerOptions();
        configureServer(httpServerOptions);
        createServer(httpServerOptions);
    }

    private HttpServerOptions defaultHttpServerOptions() {
        return new HttpServerOptions();
    }

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void createServer(HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.requestHandler(this::handleRequest);
        httpServer.listen();
    }

    protected abstract void handleRequest(final HttpServerRequest request);
}