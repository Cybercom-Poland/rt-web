package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.routing.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;

@Verticle
@Routing(URL = "/api")
public class SecondTestVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        System.out.println("Starting second test verticle");
    }

    @Routing(URL = "/methodToInvoke")
    public String methodToInvoke() {
        return "Czy aby na pewno";
    }
}
