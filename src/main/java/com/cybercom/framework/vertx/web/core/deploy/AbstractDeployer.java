package com.cybercom.framework.vertx.web.core.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.List;

public abstract class AbstractDeployer extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDeployer.class);

    @Override
    public void start() throws Exception {
        final List<VerticleWithConfig> VerticlesToDeploy = verticles();
        deployVerticles(VerticlesToDeploy);
    }

    private void deployVerticles(final List<VerticleWithConfig> verticles) {
        LOG.info("Found " + verticles.size() + " verticles");
        verticles.stream().forEach(this::deployVerticle);
    }

    private void deployVerticle(final VerticleWithConfig verticleWithConfig) {
        final Verticle verticle = verticleWithConfig.getVerticle();
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(verticleWithConfig.isWorker());

        for(int i = 0; i < verticleWithConfig.getInstances(); i++) {
            deployVerticle(verticle, deploymentOptions);
        }
    }

    private void deployVerticle(final Verticle verticle, final DeploymentOptions deploymentOptions) {
        LOG.info("Deploying: " + verticle.getClass().getSimpleName() + " worker: " + deploymentOptions.isWorker());
        getVertx().deployVerticle(verticle, deploymentOptions);
        LOG.info("Deployed: " + verticle.getClass().getSimpleName());
    }

    protected abstract List<VerticleWithConfig> verticles();
}
