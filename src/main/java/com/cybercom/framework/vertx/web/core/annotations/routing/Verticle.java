package com.cybercom.framework.vertx.web.core.annotations.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Verticle {
    boolean worker() default false;
    int instances() default 1;
}
