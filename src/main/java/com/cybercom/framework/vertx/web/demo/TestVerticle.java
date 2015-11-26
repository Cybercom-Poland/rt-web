package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.routing.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;

@Verticle
@Routing(URL = "/test")
public class TestVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        System.out.println("Test verticle started");
    }

    @Routing(URL = "/methodToInvoke")
    public String  methodToInvoke() {
        System.out.println("Invoked method: methodToInvoke");

        return "Heh dziala";
    }
}
