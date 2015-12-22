package com.cybercom.framework.vertx.web.core.verticle.method;

import com.cybercom.framework.vertx.web.core.scanner.method.MethodArgument;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodMetadata;
import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class VerticleMethods {
    private final List<MethodMetadata> methodMetadatas;

    public VerticleMethods(final List<MethodMetadata> methodMetadatas) {
        this.methodMetadatas = methodMetadatas;
    }

    public Optional<ExecutableMethod> findMethod(final Request request) {
        final String methodToInvoke = request.getMethodToInvoke();
        final com.cybercom.framework.vertx.web.core.server.http.request.Method method = request.getMethod();
        final Map<String, Object> parameters = request.getParameters();

        return findMethod(methodToInvoke, method, parameters);
    }

    private Optional<ExecutableMethod> findMethod(final String routing, final Method method, final Map<String, Object> parameters) {
        final List<MethodMetadata> methods = methodMetadatas.stream()
                                                            .filter(mwr -> isRouting(mwr, routing) && isMethodType(mwr, method))
                                                            .collect(Collectors.toList());

        return findMatchingParametersMethod(methods, parameters);
    }

    private boolean isRouting(final MethodMetadata methodMetadata, final String routing) {
        return methodMetadata.getRouting().URL().equals(routing);
    }

    private boolean isMethodType(final MethodMetadata methodMetadata, final Method method) {
        return methodMetadata.getRouting().method() == method;
    }

    private Optional<ExecutableMethod> findMatchingParametersMethod(final List<MethodMetadata> methods, final Map<String, Object> parameterValues) {
        for(final MethodMetadata methodMetadata : methods) {
            final List<MethodArgument> methodArguments = methodMetadata.getMethodArguments();

            final boolean isMethodToInvoke = methodArguments.stream().allMatch(ma -> parameterValues.containsKey(ma.getName()));

            if(isMethodToInvoke) {
                final java.lang.reflect.Method method = methodMetadata.getMethod();
                final List<Object> parameters = methodArguments.stream()
                                                               .map(ma -> parameterValues.get(ma.getName()))
                                                               .collect(Collectors.toList());

                return Optional.of(new ExecutableMethod(method, parameters));
            }
        }

        return Optional.empty();
    }

}
