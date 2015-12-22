package com.cybercom.framework.vertx.web.core.verticle.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class ExecutableMethod {
    private final Method method;
    private final Object[] parametersToInvoke;

    public ExecutableMethod(final Method method, final List<Object> parametersToInvoke) {
        this.method = method;
        this.parametersToInvoke = parametersToInvoke.toArray(new Object[parametersToInvoke.size()]);
    }

    public Object invoke(final Object parrentClass) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(parrentClass, parametersToInvoke);
    }
}
