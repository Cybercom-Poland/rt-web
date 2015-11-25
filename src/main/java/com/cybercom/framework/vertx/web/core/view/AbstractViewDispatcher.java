package com.cybercom.framework.vertx.web.core.view;


import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public abstract class AbstractViewDispatcher extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        configureRouting();
    }

    protected abstract void configureStaticResources();

    protected abstract void configureRouting();
}
