package com.cybercom.framework.vertx.web.core.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER})
public @interface MethodParameter {
    String value();
}
