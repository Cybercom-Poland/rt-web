package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.context.RoutingContextWrapper;
import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import java.util.HashMap;
import java.util.Map;

import static com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory.defaultResponseHandler;

final class DefaultGetHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGetHandler.class);
    private final EventBus eventBus;
    private Serializer serializer;

    public DefaultGetHandler(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.serializer = new DefaultSerializer();
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

        final Object body = null;
        final Map<String, Object> parameters = new HashMap<>();

        handle(routingContext, address, message, body, parameters);
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

    private void handle(final RoutingContextWrapper routingContext, final String address, final String message, final
    Object body, final Map<String, Object> parameters) {
        try {
            final JsonObject request = createRequest(address, message, body, parameters);
            eventBus.send(address, request, defaultResponseHandler(routingContext));
        } catch (SerializerException e) {
            LOG.error("Can not serialize request", e);
            routingContext.badRequest();
        }
    }

    private JsonObject createRequest(final String address, final String method, final Object body, final Map<String, Object> parameters) throws SerializerException {
        final Request request = new Request();
        request.setMethod(Method.GET);
        request.setAddress(address);
        request.setMethodToInvoke(method);
        request.setParameters(parameters);
        request.setBody(body);

        return serializer.serialize(request);
    }
}
