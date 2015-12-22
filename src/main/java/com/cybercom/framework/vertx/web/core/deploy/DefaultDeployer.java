package com.cybercom.framework.vertx.web.core.deploy;

import com.cybercom.framework.vertx.web.core.deploy.exception.DeployException;
import com.cybercom.framework.vertx.web.core.scanner.ClassCreator;
import com.cybercom.framework.vertx.web.core.scanner.ClassScanner;
import io.vertx.core.Verticle;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class DefaultDeployer extends AbstractDeployer{
    private final ClassScanner classScanner;
    
    public DefaultDeployer() {
        this.classScanner = new ClassScanner();
    }
    
    @Override
    protected List<VerticleWithConfig> verticles() {
        return getVerticlesConfigs();
    }

    private List<VerticleWithConfig> getVerticlesConfigs() {
        final List<Verticle> verticles = findVerticles();

        return buildVerticlesConfigs(verticles);
    }

    private List<Verticle> findVerticles() {
        final Set<Class<?>> classes = scanClasses();

        return createObjects(classes);
    }

    private Set<Class<?>> scanClasses() {
        return classScanner.getClassesAnnotatedWith(com.cybercom.framework.vertx.web.core.annotations.routing.Verticle.class);
    }

    private List<Verticle> createObjects(final Set<Class<?>> classes) {
        final ClassCreator classCreator = new ClassCreator();

        return classCreator.create(classes);
    }

    private List<VerticleWithConfig> buildVerticlesConfigs(final List<Verticle> verticles) {
        return verticles.stream().map(verticle -> buildVerticleConfig(verticle)).collect(Collectors.toList());
    }
    
    private VerticleWithConfig buildVerticleConfig(final Verticle verticle) {
        final Optional<com.cybercom.framework.vertx.web.core.annotations.routing.Verticle> annotation
                = classScanner.getAnnotation(verticle.getClass(), com.cybercom.framework.vertx.web.core.annotations.routing.Verticle.class);

        final com.cybercom.framework.vertx.web.core.annotations.routing.Verticle verticleAnnotation = annotation
                .orElseThrow(() -> new DeployException("Can not deploy " + verticle.getClass()));

        return new VerticleWithConfig(verticle, verticleAnnotation.instances(), verticleAnnotation.worker());
    }
}
