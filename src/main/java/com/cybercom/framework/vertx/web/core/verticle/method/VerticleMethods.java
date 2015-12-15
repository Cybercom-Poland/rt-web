package com.cybercom.framework.vertx.web.core.verticle.method;

import com.cybercom.framework.vertx.web.core.scanner.method.MethodWithRouting;
import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import java.util.List;
import java.util.Optional;

public final class VerticleMethods {
    private final List<MethodWithRouting> methodWithRoutings;

    public VerticleMethods(final List<MethodWithRouting> methodWithRoutings) {
        this.methodWithRoutings = methodWithRoutings;
    }

    public Optional<MethodWithRouting> findMethod(final String routing, final Method method) {
        return methodWithRoutings.stream().filter(mwr -> mwr.getRouting().URL().equals(routing) && mwr.getRouting().method() == method).findFirst();
    }
}
