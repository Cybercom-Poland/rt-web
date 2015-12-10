package com.cybercom.framework.vertx.web.core.server.http.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;


public class DefaultWebSocketResponseHandler implements Handler<AsyncResult<Message<Object>>> {
    private final SockJSSocket sockJSSocket;

    public DefaultWebSocketResponseHandler(SockJSSocket sockJSSocket) {
        this.sockJSSocket = sockJSSocket;
    }

    @Override
    public void handle(AsyncResult<Message<Object>> event) {
        if (event.succeeded()) {
            sockJSSocket.write(Buffer.buffer(event.result().body().toString()));
        } else {
            sockJSSocket.write(Buffer.buffer("failure ;("));
        }

    }
}
