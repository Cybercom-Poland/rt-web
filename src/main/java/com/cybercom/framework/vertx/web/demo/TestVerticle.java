package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.routing.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@Verticle(instances = 2, worker = true)
@Routing(URL = "/test")
public class TestVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(SecondTestVerticle.class);

    @Override
    public void start() throws Exception {
        LOG.info("Test verticle started");
    }

    @Routing(URL = "/methodToInvoke")
    public String methodToInvoke() {
        LOG.info("Invoked method: methodToInvoke");

        return "Heh dziala";
    }

    @Routing(URL = "/secondMethod")
    public String secondMethod() {
        LOG.info("Invoked method: secondMethod");

        return "ostro";
    }
}
