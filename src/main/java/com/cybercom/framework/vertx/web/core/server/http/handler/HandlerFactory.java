package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public final class HandlerFactory {
    private HandlerFactory() {
    }

    public static Handler<AsyncResult<Message<Object>>> defaultResponseHandler(final RoutingContextWrapper routingContext) {
        return new DefaultResponseHandler<>(routingContext);
    }

    public static Handler<AsyncResult<Message<Object>>> defaultWebSocketResponseHandler(final ServerWebSocket serverWebSocket) {
        return new DefaultWebSocketResponseHandler(serverWebSocket);
    }

    public static Handler<RoutingContext> defaultRestRequestHandler(final EventBus eventBus, final Method method) {
        return new DefaultRestRequestHandler(eventBus, method);
    }

    public static Handler<ServerWebSocket> defaultWebSocketHandler(final Vertx vertx, final String contextPath) {
        return new DefaultWebSocketHandler(vertx, contextPath);
    }

    public static Handler<RoutingContext> defaultStaticResourceHandler() {
        return StaticHandler.create();
    }
}
