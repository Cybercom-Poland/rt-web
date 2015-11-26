package com.cybercom.framework.vertx.web.core.scanner.method;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import java.lang.reflect.Method;

public class MethodWithRouting {
    private final Method method;
    private final Routing routing;

    public MethodWithRouting(Method method, Routing routing) {
        this.method = method;
        this.routing = routing;
    }

    public Method getMethod() {
        return method;
    }

    public Routing getRouting() {
        return routing;
    }
}
