package com.cybercom.framework.vertx.web.core.deploy;

import io.vertx.core.Verticle;

public final class VerticleConfig {
    private final Verticle verticle;
    private final int instances;
    private final boolean worker;

    public VerticleConfig(final Verticle verticle, final int instances, final boolean worker) {
        this.verticle = verticle;
        this.instances = instances;
        this.worker = worker;
    }

    public Verticle getVerticle() {
        return verticle;
    }

    public int getInstances() {
        return instances;
    }

    public boolean isWorker() {
        return worker;
    }
}
