package com.cybercom.framework.vertx.web.core.scanner;

import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodWithRouting;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.reflections.Reflections;

public final class ClassScanner {
    public Set<Class<?>> getClassesAnnotatedWith(final Class<? extends Annotation> annotation) {
        //TODO change hardcoded string
        final Reflections reflections = new Reflections("com.cybercom.framework.vertx.web.demo");

        return reflections.getTypesAnnotatedWith(annotation);
    }

    public List<MethodWithRouting> getMethodsWithRouting(final Class<?> classz) {
        final List<MethodWithRouting> methodWithRoutings = new ArrayList<>();

        final Method[] methods = classz.getMethods();

        for(Method method : methods) {
            final Optional<Routing> annotationOptional = getAnnotation(method, Routing.class);
            annotationOptional.ifPresent(annotation -> methodWithRoutings.add(new MethodWithRouting(method, annotation)));
        }

        return methodWithRoutings;
    }

    public <T> Optional<T> getAnnotation(final AnnotatedElement annotatedElement, final Class<T> annotationToFind) {
        final Annotation[] annotations = annotatedElement.getAnnotations();

        return (Optional<T>) Stream.of(annotations).filter(
                annotation -> annotation.annotationType().equals(annotationToFind)).findFirst();
    }
}
