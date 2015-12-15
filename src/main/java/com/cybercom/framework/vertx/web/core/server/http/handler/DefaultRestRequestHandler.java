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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.cybercom.framework.vertx.web.core.server.http.handler.HandlerFactory.defaultResponseHandler;

final class DefaultRestRequestHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultRestRequestHandler.class);
    private final EventBus eventBus;
    private final Method method;
    private Serializer serializer;

    public DefaultRestRequestHandler(final EventBus eventBus, final Method method) {
        this.eventBus = eventBus;
        this.serializer = new DefaultSerializer();
        this.method = method;
    }

    @Override
    public void handle(final RoutingContext routingContextArg) {
        final RoutingContextWrapper routingContext = new RoutingContextWrapper(routingContextArg);

        final String uri = routingContext.uri();
        final String address = getAddressFromUri(uri);
        final String methodToInvoke = getMethodFromUri(uri);

        if (address.isEmpty() || methodToInvoke.isEmpty()) {
            routingContext.badRequest();
            return;
        }

        final Object body = null;
        final Map<String, Object> parameters = new HashMap<>();

        handle(routingContext, address, methodToInvoke, body, parameters);
    }

    private String getAddressFromUri(String uri) {
        List<String> uriPartials = splitUri(uri);
        if (!uriPartials.isEmpty()) {
            int prefixIndex = 0;
            uriPartials.remove(prefixIndex);
        }
        for (String partial : uriPartials) {
            if (partial.length() > 1) {
                return partial;
            }
        }
        return "";
    }

    private String getMethodFromUri(String uri) {
        List<String> uriPartials = splitUri(uri);
        if (uriPartials.size() < 2) {
            return "";
        }
        uriPartials.subList(0, 2).clear();

        StringJoiner message = new StringJoiner("");
        uriPartials.stream().filter(partial -> partial.length() > 1).forEach(message::add);

        return message.toString();
    }

    private List<String> splitUri(String uri) {
        return new ArrayList<>(Arrays.asList(uri.split("(?=/)")));
    }

    private void handle(final RoutingContextWrapper routingContext, final String address, final String methodToInvoke, final
    Object body, final Map<String, Object> parameters) {
        try {
            Request request = new Request.RequestBuilder(address, methodToInvoke).body(body).parameters(parameters).method(method). build();
            final JsonObject requestJson = serializer.serialize(request);
            eventBus.send(address, requestJson, defaultResponseHandler(routingContext));
        } catch (SerializerException e) {
            LOG.error("Can not serialize request", e);
            routingContext.badRequest();
        }
    }
}
