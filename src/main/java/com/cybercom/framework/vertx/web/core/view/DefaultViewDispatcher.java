package com.cybercom.framework.vertx.web.core.view;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class DefaultViewDispatcher extends AbstractViewDispatcher {
    @Override
    protected void configureStaticResources(Router router) {
        router.route("/assets/*").handler(StaticHandler.create("assets"));
    }

}

