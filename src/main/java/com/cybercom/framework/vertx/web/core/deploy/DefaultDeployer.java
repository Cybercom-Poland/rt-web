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
        final ClassScanner classScanner = new ClassScanner();
        final Set<Class<?>> classes = classScanner.getClassesAnnotatedWith(com.cybercom.framework.vertx.web.core.annotation.Verticle.class);
        final ClassCreator classCreator = new ClassCreator();

        return classCreator.create(classes);
    }
}
