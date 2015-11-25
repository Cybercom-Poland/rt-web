package com.cybercom.framework.vertx.web.core.view;


import com.cybercom.framework.vertx.web.core.messages.MessageCodes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;

import java.time.LocalDateTime;

public abstract class AbstractViewDispatcher extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        configureRouting();
    }

    protected void configureRouting() {
        EventBus eb = vertx.eventBus();
        eb.consumer(MessageCodes.MESSAGE_GET_VIEW, msgObj -> {
            System.out.println(msgObj.body().toString());
            msgObj.reply("index.html");
        });
    }
}
