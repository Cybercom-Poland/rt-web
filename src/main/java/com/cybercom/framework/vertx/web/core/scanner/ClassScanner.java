package com.cybercom.framework.vertx.web.core.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public final class ClassScanner {
    public Set<Class<?>> getClassesAnnotatedWith(final Class<? extends Annotation> annotation) {
        //TODO change hardcoded string
        final Reflections reflections = new Reflections("com.cybercom.framework.vertx.web.demo");

        return reflections.getTypesAnnotatedWith(annotation);
    }
}
