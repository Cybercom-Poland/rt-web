package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;

public final class HandlerFactory {
    private HandlerFactory() {}

    public static Handler<AsyncResult<Message<Object>>> defaultResponseHandler(final RoutingContextWrapper
            routingContext) {
        return new DefaultResponseHandler<>(routingContext);
    }

    public static Handler<RoutingContext> defaultGetHandler(final EventBus eventBus) {
        return new DefaultGetHandler(eventBus);
    }
}
