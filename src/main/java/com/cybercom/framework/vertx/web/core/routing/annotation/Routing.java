package com.cybercom.framework.vertx.web.core.routing.annotation;

import com.cybercom.framework.vertx.web.core.server.http.request.Method;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE, ElementType.METHOD})
public @interface Routing {
    String URL();
    Method method() default Method.GET;
}
