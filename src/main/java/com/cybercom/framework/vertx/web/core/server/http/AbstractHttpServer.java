package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractHttpServer extends AbstractVerticle {
    private static final String API_PATH = "/api/*";

    private Router mainRouter;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        this.mainRouter = Router.router(vertx);
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
        final Handler<RoutingContext> staticResourcesHandler = HandlerFactory.defaultStaticResourceHandler();
        final Handler<RoutingContext> getHandler = HandlerFactory.defaultGetHandler(eventBus);
        mainRouter.get(API_PATH).handler(getHandler);
        mainRouter.route().handler(staticResourcesHandler);
    }

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void createServer(final HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.websocketHandler(ws -> {
            if (ws.path().equals("/api")) {
                ws.handler(data -> ws.writeFinalTextFrame("just read from input following data: " + data.toString()));
            } else {
                ws.reject();
            }
        });
        httpServer.requestHandler(mainRouter::accept);
        httpServer.listen();
    }
}
