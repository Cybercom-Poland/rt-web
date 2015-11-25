package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.messages.MessageCodes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public abstract class AbstractHttpServer extends AbstractVerticle {
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

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void configureRouting() {
        EventBus eb = vertx.eventBus();
        //router.route("/assets/*").handler(StaticHandler.create());
        router.get("/").handler(routingContext -> {
            eb.send(MessageCodes.MESSAGE_GET_VIEW, routingContext.request().uri(), response -> {
                routingContext.response().end();
            });

        });
    }

    private void createServer(HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.requestHandler(router::accept);
        httpServer.listen();
    }
}
