package com.cybercom.framework.vertx.web.core.verticle;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.scanner.ClassScanner;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodWithRouting;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractVerticle extends io.vertx.core.AbstractVerticle {
    private final List<MethodWithRouting> methodWithRoutings;

    public AbstractVerticle() {
        methodWithRoutings = new ArrayList<>();
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        addRouting(vertx);
    }

    private void addRouting(final Vertx vertx) {
        final ClassScanner classScanner = new ClassScanner();
        final Optional<Routing> annotationFromClass = classScanner.getAnnotation(this.getClass(), Routing.class);

        if(annotationFromClass.isPresent()) {
            final Routing routing = annotationFromClass.get();
            vertx.eventBus().consumer(routing.URL(), this::handleMethodRequest);

            final List<MethodWithRouting> methodsWithRouting = classScanner.getMethodsWithRouting(this.getClass());
            methodWithRoutings.addAll(methodsWithRouting);
        }
    }

    //TODO Object to better object (String for test) and parameters support. Need codec support
    private void handleMethodRequest(Message<String> request) {
        final String body = request.body();
        final Optional<MethodWithRouting> methodToInvoke = findMethodToInvoke(body);

        if(methodToInvoke.isPresent()) {
            final MethodWithRouting methodWithRouting = methodToInvoke.get();
            final Method method = methodWithRouting.getMethod();

            final Object response = invokeMethod(method);
            request.reply(response);
        }
    }

    private Optional<MethodWithRouting> findMethodToInvoke(final String routing) {
        return methodWithRoutings.stream().filter(mwr -> mwr.getRouting().URL().equals(routing)).findFirst();
    }

    private Object invokeMethod(Method method) {
        try {
            return method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object();
    }
}
