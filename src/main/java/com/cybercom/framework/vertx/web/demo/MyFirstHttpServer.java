package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.routing.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.server.http.AbstractHttpServer;
import io.vertx.core.http.HttpServerOptions;

@Verticle
public class MyFirstHttpServer extends AbstractHttpServer {
    @Override
    protected void configureServer(HttpServerOptions httpServerOptions) {
        httpServerOptions.setPort(8080);
    }
}
