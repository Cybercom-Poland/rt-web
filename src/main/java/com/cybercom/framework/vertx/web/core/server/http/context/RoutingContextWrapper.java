package com.cybercom.framework.vertx.web.core.server.http.context;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.ext.web.RoutingContext;

public final class RoutingContextWrapper {
    private final RoutingContext routingContext;

    public RoutingContextWrapper(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public String uri() {
        return routingContext.request().uri();
    }

    public void badRequest() {
        routingContext.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end();
    }

    public void response(final Object object) {
        routingContext.response().end(object.toString());
    }
}
