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
        final List<VerticleConfig> VerticlesToDeploy = verticles();
        deployVerticles(VerticlesToDeploy);
    }

    private void deployVerticles(final List<VerticleConfig> verticles) {
        LOG.info("Found " + verticles.size() + " verticles");
        verticles.stream().forEach(this::deployVerticle);
    }

    private void deployVerticle(final VerticleConfig verticleConfig) {
        final Verticle verticle = verticleConfig.getVerticle();
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(verticleConfig.isWorker());

        for(int i=0; i < verticleConfig.getInstances(); i++) {
            deployVerticle(verticle, deploymentOptions);
        }
    }

    private void deployVerticle(final Verticle verticle, final DeploymentOptions deploymentOptions) {
        LOG.info("Deploying: " + verticle.getClass().getSimpleName() + " instances: " + deploymentOptions
                .getInstances() + " worker: " + deploymentOptions.isWorker());
        getVertx().deployVerticle(verticle, deploymentOptions);
        LOG.info("Deployed: " + verticle.getClass().getSimpleName());
    }

    protected abstract List<VerticleConfig> verticles();
}
