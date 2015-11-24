package com.cybercom.framework.vertx.web.core.example;

import com.cybercom.framework.vertx.web.core.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.server.http.AbstractHttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import java.time.LocalDateTime;

@Verticle
public class MyFirstHttpServer extends AbstractHttpServer {
    @Override
    protected void configureServer(HttpServerOptions httpServerOptions) {
        httpServerOptions.setPort(8080);
    }

    @Override
    protected void handleRequest(final HttpServerRequest request) {
        request.response().end(LocalDateTime.now().toString());
    }
}
