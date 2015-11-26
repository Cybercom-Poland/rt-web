package com.cybercom.framework.vertx.web.core.server.http;

import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

public abstract class AbstractHttpServer extends AbstractVerticle {
    private Router router;

    public AbstractHttpServer() {
        this.router = Router.router(vertx);
    }

    @Override
    public void start() throws Exception {
        final HttpServerOptions httpServerOptions = defaultHttpServerOptions();
        configureRouting();
        configureServer(httpServerOptions);
        createServer(httpServerOptions);
    }

    private HttpServerOptions defaultHttpServerOptions() {
        return new HttpServerOptions();
    }

    protected abstract void configureServer(final HttpServerOptions httpServerOptions);

    private void configureRouting() {
        EventBus eb = vertx.eventBus();
        router.get("/*").handler(routingContext -> {
            String uri = routingContext.request().uri();
            String address = getAddressFromUri(uri);
            String message = getMessageFromUri(uri);

            if (address.isEmpty() || message.isEmpty()) {
                routingContext.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end();
                return;
            }

            eb.send(address, message, response -> {
                if (response.succeeded()) {
                    System.out.println("yeah, address recognized " + address + message);
                    routingContext.response().end(response.result().body().toString());
                } else {
                    System.out.println("nope, can't reconigze that address " + address + message);
                    routingContext.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end();
                }
            });
        });
    }

    private void createServer(HttpServerOptions httpServerOptions) {
        final HttpServer httpServer = getVertx().createHttpServer(httpServerOptions);
        httpServer.requestHandler(router::accept);
        httpServer.listen();
    }


    private String[] splitUri(String uri) {
        return uri.split("(?=/)");
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
