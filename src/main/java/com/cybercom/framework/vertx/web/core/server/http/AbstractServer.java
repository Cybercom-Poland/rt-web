package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public abstract class AbstractServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        final HttpServerOptions httpServerOptions = defaultHttpServerOptions();
        configureServer(httpServerOptions);
        createServer(httpServerOptions);
    }

    private HttpServerOptions defaultHttpServerOptions() {
        return new HttpServerOptions();
    }

    protected void configureServer(final HttpServerOptions httpServerOptions){}

    private void createServer(final HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        configureServer(httpServer);
        httpServer.listen();
    }

    protected abstract void configureServer(final HttpServer httpServer);
}
