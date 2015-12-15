package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory;
import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class DefaultHttpServer extends AbstractServer {
    private static final String NO_CONTEXT = "/";
    private final String contextPath;
    private Router mainRouter;

    public DefaultHttpServer(final String contextPath) {
        this.contextPath = contextPath;
    }

    public DefaultHttpServer() {
        contextPath = NO_CONTEXT;
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        this.mainRouter = Router.router(vertx);
    }

    @Override
    protected void configureServer(final HttpServer httpServer) {
        final EventBus eventBus = vertx.eventBus();

        //TODO is good place for this ? hmmm
        final Handler<RoutingContext> getHandler = HandlerFactory.defaultRestRequestHandler(eventBus, Method.GET);
        mainRouter.get(contextPath).handler(getHandler);

        final Handler<RoutingContext> postHandler = HandlerFactory.defaultRestRequestHandler(eventBus, Method.POST);
        mainRouter.post(contextPath).handler(postHandler);

        final Handler<RoutingContext> staticResourcesHandler = HandlerFactory.defaultStaticResourceHandler();
        mainRouter.route().handler(staticResourcesHandler);

        httpServer.requestHandler(mainRouter::accept);
    }
}
