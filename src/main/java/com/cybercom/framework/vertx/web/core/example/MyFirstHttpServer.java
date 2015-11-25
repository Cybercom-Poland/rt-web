package com.cybercom.framework.vertx.web.core.example;

import com.cybercom.framework.vertx.web.core.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.server.http.AbstractHttpServer;
import io.vertx.core.http.HttpServerOptions;

@Verticle
public class MyFirstHttpServer extends AbstractHttpServer {
    @Override
    protected void configureServer(HttpServerOptions httpServerOptions) {
        httpServerOptions.setPort(8080);
    }

}
