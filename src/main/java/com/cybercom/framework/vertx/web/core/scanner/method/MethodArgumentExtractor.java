package com.cybercom.framework.vertx.web.core.scanner.method;

import com.cybercom.framework.vertx.web.core.annotations.method.MethodParameter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodArgumentExtractor {
    public List<MethodArgument> extract(final Method method) {
        final List<MethodArgument> methodArguments = new ArrayList<>();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        int i = 0;
        for(Annotation[] annotatnions : parameterAnnotations) {
            final Class parameterType = parameterTypes[i++];

            for(Annotation annotation : annotatnions){
                if(annotation instanceof MethodParameter){
                    final MethodParameter methodParameter = (MethodParameter) annotation;
                    final String argumentName = methodParameter.value();
                    final MethodArgument methodArgument = new MethodArgument(argumentName, parameterType, false);

                    methodArguments.add(methodArgument);
                }
            }
        }

        return methodArguments;
    }
}
