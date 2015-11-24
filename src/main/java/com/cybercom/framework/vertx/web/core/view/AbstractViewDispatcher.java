package com.cybercom.framework.vertx.web.core.view;


import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public abstract class AbstractViewDispatcher extends AbstractVerticle {
    private Router router = Router.router(vertx);


    @Override
    public void start() throws Exception {

    }

    protected abstract void configureStaticResources(Router router);
}
