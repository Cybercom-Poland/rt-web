package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

final class DefaultResponseHandler<T> implements Handler<AsyncResult<Message<T>>> {
    private final  RoutingContextWrapper routingContext;

    public DefaultResponseHandler(RoutingContextWrapper routingContext) {
        this.routingContext = routingContext;
    }

    @Override
    public void handle(final AsyncResult<Message<T>> event) {
        if (event.succeeded()) {
            routingContext.response(event.result().body().toString());
        } else {
            routingContext.badRequest();
        }
    }
}
