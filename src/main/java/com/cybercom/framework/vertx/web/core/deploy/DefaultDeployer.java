package com.cybercom.framework.vertx.web.core.deploy;

import com.cybercom.framework.vertx.web.core.scanner.ClassCreator;
import com.cybercom.framework.vertx.web.core.scanner.ClassScanner;
import io.vertx.core.Verticle;
import java.util.List;
import java.util.Set;

public final class DefaultDeployer extends AbstractDeployer{
    @Override
    protected List<Verticle> verticles() {
        return searchVerticles();
    }

    private List<Verticle> searchVerticles() {
        final Set<Class<?>> classes = searchClasses();

        return createObjects(classes);
    }

    private Set<Class<?>> searchClasses() {
        final ClassScanner classScanner = new ClassScanner();

        return classScanner.getClassesAnnotatedWith(com.cybercom.framework.vertx.web.core.routing.annotation.Verticle.class);
    }

    private List<Verticle> createObjects(final Set<Class<?>> classes) {
        final ClassCreator classCreator = new ClassCreator();

        return classCreator.create(classes);
    }
}
