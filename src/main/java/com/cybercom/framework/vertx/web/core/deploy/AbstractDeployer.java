package com.cybercom.framework.vertx.web.core.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import java.util.List;

public abstract class AbstractDeployer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        final List<Verticle> VerticlesToDeploy = verticles();
        deployVerticles(VerticlesToDeploy);
    }

    private void deployVerticles(final List<Verticle> verticles) {
        verticles.stream().forEach(getVertx()::deployVerticle);
    }

    protected abstract List<Verticle> verticles();
}
