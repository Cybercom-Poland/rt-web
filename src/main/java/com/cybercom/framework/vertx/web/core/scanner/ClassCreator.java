package com.cybercom.framework.vertx.web.core.scanner;

import com.cybercom.framework.vertx.web.core.scanner.exception.ClassCreationException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ClassCreator {
    public <T> List<T> create(final Set<Class<?>> classes){
        try {
            return createUnsafe(classes);
        } catch (Exception e) {
           throw new ClassCreationException("Error while creating classes", e);
        }
    }

    private <T> List<T> createUnsafe(Set<Class<?>> classes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final List<T> objects = new ArrayList<>(classes.size());

        for(Class<?> singleClass : classes) {
            final T classInstance = create(singleClass);
            objects.add(classInstance);
        }

        return objects;
    }

    private <T> T create(final Class<?> singleClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (T) singleClass.getConstructor().newInstance();
    }
}
