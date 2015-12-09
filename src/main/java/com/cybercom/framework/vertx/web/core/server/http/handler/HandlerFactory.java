package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;

public final class HandlerFactory {
    private HandlerFactory() {
    }

    public static Handler<AsyncResult<Message<Object>>> defaultResponseHandler(final RoutingContextWrapper routingContext) {
        return new DefaultResponseHandler<>(routingContext);
    }

    public static Handler<AsyncResult<Message<Object>>> defaultWebSocketResponseHandler(final SockJSSocket sockJSHandlerHandler) {
        return new DefaultWebSocketResponseHandler(sockJSHandlerHandler);
    }

    public static Handler<RoutingContext> defaultGetHandler(final EventBus eventBus) {
        return new DefaultGetHandler(eventBus);
    }

    public static Handler<RoutingContext> defaultWebSocketHandler(final Vertx vertx) {
        return new DefaultWebSocketHandler(vertx);
    }

    public static Handler<RoutingContext> defaultStaticResourceHandler() {
        return StaticHandler.create();
    }
}
