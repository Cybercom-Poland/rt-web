package com.cybercom.framework.vertx.web.core.server.http.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;


public class DefaultWebSocketResponseHandler implements Handler<AsyncResult<Message<Object>>> {
    private final ServerWebSocket serverWebSocket;

    public DefaultWebSocketResponseHandler(final ServerWebSocket serverWebSocket) {
        this.serverWebSocket = serverWebSocket;
    }

    @Override
    public void handle(AsyncResult<Message<Object>> event) {
        if (event.succeeded()) {
            serverWebSocket.writeFinalTextFrame(event.result().body().toString());
        } else {
            serverWebSocket.writeFinalTextFrame("failure ;(");
        }

    }
}
