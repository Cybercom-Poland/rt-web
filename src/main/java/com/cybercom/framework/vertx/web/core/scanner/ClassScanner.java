package com.cybercom.framework.vertx.web.core.scanner;

import com.cybercom.framework.vertx.web.core.annotations.routing.Routing;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodArgument;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodArgumentExtractor;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodMetadata;
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
    private final MethodArgumentExtractor methodArgumentExtractor;

    public ClassScanner() {
        this.methodArgumentExtractor = new MethodArgumentExtractor();
    }

    public Set<Class<?>> getClassesAnnotatedWith(final Class<? extends Annotation> annotation) {
        //TODO change hardcoded string
        final Reflections reflections = new Reflections("com.cybercom.framework.vertx.web.demo");

        return reflections.getTypesAnnotatedWith(annotation);
    }

    public List<MethodMetadata> getMethodsWithRouting(final Class<?> classz) {
        final List<MethodMetadata> methodMetadatas = new ArrayList<>();

        final Method[] methods = classz.getMethods();

        for(Method method : methods) {
            final Optional<Routing> annotationOptional = getAnnotation(method, Routing.class);
            final List<MethodArgument> arguments = getArguments(method);

            annotationOptional.ifPresent(annotation -> methodMetadatas.add(new MethodMetadata(method, annotation, arguments)));
        }

        return methodMetadatas;
    }

    private List<MethodArgument> getArguments(final Method method) {
        return methodArgumentExtractor.extract(method);
    }

    public <T> Optional<T> getAnnotation(final AnnotatedElement annotatedElement, final Class<T> annotationToFind) {
        final Annotation[] annotations = annotatedElement.getAnnotations();

        return (Optional<T>) Stream.of(annotations).filter(annotation -> annotation.annotationType().equals(annotationToFind)).findFirst();
    }
}
