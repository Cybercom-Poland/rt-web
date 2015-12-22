package com.cybercom.framework.vertx.web.core.scanner.method;

import com.cybercom.framework.vertx.web.core.annotations.routing.Routing;
import java.lang.reflect.Method;
import java.util.List;

public class MethodMetadata {
    private final Method method;
    private final Routing routing;
    private final List<MethodArgument> methodArguments;

    public MethodMetadata(final Method method, final Routing routing, final List<MethodArgument> methodArguments) {
        this.method = method;
        this.routing = routing;
        this.methodArguments = methodArguments;
    }

    public Method getMethod() {
        return method;
    }

    public Routing getRouting() {
        return routing;
    }

    public List<MethodArgument> getMethodArguments() {
        return methodArguments;
    }
}
