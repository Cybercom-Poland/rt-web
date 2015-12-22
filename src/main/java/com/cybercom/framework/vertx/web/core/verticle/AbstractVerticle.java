package com.cybercom.framework.vertx.web.core.verticle;

import com.cybercom.framework.vertx.web.core.annotations.routing.Routing;
import com.cybercom.framework.vertx.web.core.scanner.ClassScanner;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodMetadata;
import com.cybercom.framework.vertx.web.core.verticle.handler.VerticleMethodInvoker;
import com.cybercom.framework.vertx.web.core.verticle.method.VerticleMethods;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import java.util.List;
import java.util.Optional;

public abstract class AbstractVerticle extends io.vertx.core.AbstractVerticle {

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        addRouting(vertx);
    }

    private void addRouting(final Vertx vertx) {
        final ClassScanner classScanner = new ClassScanner();
        final Optional<Routing> annotationFromClass = classScanner.getAnnotation(this.getClass(), Routing.class);

        if(annotationFromClass.isPresent()) {
            final List<MethodMetadata> methodsWithRouting = classScanner.getMethodsWithRouting(this.getClass());
            final VerticleMethods verticleMethods = new VerticleMethods(methodsWithRouting);

            final VerticleMethodInvoker verticleMethodInvoker = new VerticleMethodInvoker(verticleMethods, this);
            final Routing routing = annotationFromClass.get();
            vertx.eventBus().consumer(routing.URL(), verticleMethodInvoker);
        }
    }
}
