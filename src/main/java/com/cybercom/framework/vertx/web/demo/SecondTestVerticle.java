package com.cybercom.framework.vertx.web.demo;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.routing.annotation.Verticle;
import com.cybercom.framework.vertx.web.core.verticle.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@Verticle
@Routing(URL = "/second")
public class SecondTestVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(SecondTestVerticle.class);

    @Override
    public void start() throws Exception {
       LOG.info("Starting second test verticle");
    }

    @Routing(URL = "/methodToInvoke")
    public String methodToInvoke() {
        return "Czy aby na pewno";
    }
}
