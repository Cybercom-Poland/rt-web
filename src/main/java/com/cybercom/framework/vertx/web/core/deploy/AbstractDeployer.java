package com.cybercom.framework.vertx.web.core.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.List;

public abstract class AbstractDeployer extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDeployer.class);

    @Override
    public void start() throws Exception {
        final List<Verticle> VerticlesToDeploy = verticles();
        deployVerticles(VerticlesToDeploy);
    }

    private void deployVerticles(final List<Verticle> verticles) {
        LOG.info("Found " + verticles.size() + " verticles");
        verticles.stream().forEach(this::deployVerticle);
    }

    private void deployVerticle(final Verticle verticle) {
        LOG.info("Deploying: " + verticle.getClass());
        getVertx().deployVerticle(verticle);
        LOG.info("Deployed: " + verticle.getClass());
    }

    protected abstract List<Verticle> verticles();
}
