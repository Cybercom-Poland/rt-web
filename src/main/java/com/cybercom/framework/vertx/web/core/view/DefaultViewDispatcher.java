package com.cybercom.framework.vertx.web.core.view;

import com.cybercom.framework.vertx.web.core.annotation.Verticle;
import io.vertx.core.eventbus.EventBus;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Verticle
public class DefaultViewDispatcher extends AbstractViewDispatcher {
    public final static String HTTP_SERVER_GET_EVENT = "HttpServer.get";

    @Override
    protected void configureStaticResources() {
        //        router.route("/assets/*").handler(StaticHandler.create("assets"));
    }

    @Override
    protected void configureRouting() {
        EventBus eb = vertx.eventBus();
        eb.consumer(HTTP_SERVER_GET_EVENT, msgObj -> {
            System.out.println("message obj = " + msgObj.body().toString());
            msgObj.reply("<h1> data </h1> " + LocalDateTime.now());
        });

    }


}

