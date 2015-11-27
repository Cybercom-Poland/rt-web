package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

import static com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory.defaultResponseHandler;

final class DefaultGetHandler implements Handler<RoutingContext> {
    private final EventBus eventBus;

    public DefaultGetHandler(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void handle(final RoutingContext routingContextArg) {
        final RoutingContextWrapper routingContext = new RoutingContextWrapper(routingContextArg);

        final String uri = routingContext.uri();
        final String address = getAddressFromUri(uri);
        final String message = getMessageFromUri(uri);

        if (address.isEmpty() || message.isEmpty()) {
            routingContext.badRequest();
            return;
        }

       eventBus.send(address, message, defaultResponseHandler(routingContext));
    }

    private String getAddressFromUri(String uri) {
        String[] uriPartials = splitUri(uri);
        for (String partial : uriPartials) {
            if (partial.length() > 1) {
                return partial;
            }
        }
        return "";
    }

    private String[] splitUri(String uri) {
        return uri.split("(?=/)");
    }

    private String getMessageFromUri(String uri) {
        if (uri.isEmpty()) {
            return "";
        }
        int indexOfSecondSlash = uri.indexOf("/", 1);
        if (indexOfSecondSlash > -1) {
            return uri.substring(indexOfSecondSlash);
        }
        return "";
    }
}
