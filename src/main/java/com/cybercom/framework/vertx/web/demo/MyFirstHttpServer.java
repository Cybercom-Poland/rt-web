package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.annotations.routing.Verticle;
import com.cybercom.framework.vertx.web.core.server.http.DefaultHttpServer;
import io.vertx.core.http.HttpServerOptions;

@Verticle
public class MyFirstHttpServer extends DefaultHttpServer {
    private static final String API_PATH = "/api/*";

    public MyFirstHttpServer() {
        super(API_PATH);
    }

    @Override
    protected void configureServer(HttpServerOptions httpServerOptions) {
        httpServerOptions.setPort(8080);
    }
}
