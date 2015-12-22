package com.cybercom.framework.vertx.web.core.server.http.handler;

import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

final public class DefaultWebSocketHandler implements Handler<ServerWebSocket> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultWebSocketHandler.class);
    private final Serializer serializer;
    private final EventBus eventBus;
    private final String contextPath;

    public DefaultWebSocketHandler(final Vertx vertx, final String contextPath) {
        this.serializer = new DefaultSerializer();
        this.eventBus = vertx.eventBus();
        this.contextPath = contextPath;
    }

    @Override
    public void handle(final ServerWebSocket webSocket) {
        if(isSupportedContextPath(webSocket)) {
            webSocket.handler(buffer -> handleBuffer(webSocket, buffer));
        } else {
            webSocket.reject();
        }
    }

    private boolean isSupportedContextPath(final ServerWebSocket webSocket) {
        return webSocket.path().equals(contextPath);
    }

    private void handleBuffer(final ServerWebSocket webSocket, final Buffer buffer) {
        final JsonObject jsonRequest = new JsonObject(buffer.toString());
        handleBuffer(webSocket, jsonRequest);
    }

    private void handleBuffer(final ServerWebSocket webSocket, final JsonObject jsonRequest) {
        try {
            final Request request = serializer.deserialize(jsonRequest, Request.class);
            eventBus.send(request.getAddress(), jsonRequest, HandlerFactory.defaultWebSocketResponseHandler(webSocket));
        } catch (SerializerException e) {
            LOG.error("Can not deserialize request", e);
            //TODO rethink error handling
            webSocket.write(Buffer.buffer("An error occured"));
        }
    }
}
