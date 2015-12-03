package com.cybercom.framework.vertx.web.core.scanner;

public final class ClassWithAnnotation <T> {
    private final Class<?> classes;
    private final Class<T> annotation;

    public ClassWithAnnotation(final Class<?> classes, final Class<T> annotation) {
        this.classes = classes;
        this.annotation = annotation;
    }

    public Class<?> getClasses() {
        return classes;
    }

    public Class<T> getAnnotation() {
        return annotation;
    }
}
