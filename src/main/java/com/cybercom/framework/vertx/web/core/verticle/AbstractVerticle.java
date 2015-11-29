package com.cybercom.framework.vertx.web.core.verticle;

import com.cybercom.framework.vertx.web.core.error.ErrorCodes;
import com.cybercom.framework.vertx.web.core.routing.annotation.Routing;
import com.cybercom.framework.vertx.web.core.scanner.ClassScanner;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodWithRouting;
import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractVerticle extends io.vertx.core.AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractVerticle.class);

    private final List<MethodWithRouting> methodWithRoutings;
    private final Serializer serializer;

    public AbstractVerticle() {
        methodWithRoutings = new ArrayList<>();
        serializer = new DefaultSerializer();
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        addRouting(vertx);
    }

    private void addRouting(final Vertx vertx) {
        final ClassScanner classScanner = new ClassScanner();
        final Optional<Routing> annotationFromClass = classScanner.getAnnotation(this.getClass(), Routing.class);

        if(annotationFromClass.isPresent()) {
            final Routing routing = annotationFromClass.get();
            vertx.eventBus().consumer(routing.URL(), this::handleMethodRequest);

            final List<MethodWithRouting> methodsWithRouting = classScanner.getMethodsWithRouting(this.getClass());
            methodWithRoutings.addAll(methodsWithRouting);
        }
    }

    private void handleMethodRequest(final Message<JsonObject> requestMessage) {
        try {
            final Request request = serializer.deserialize(requestMessage.body(), Request.class);
            handleRequest(request, requestMessage);
        } catch (SerializerException e) {
            LOG.error("Can not deserialize request", e);
            requestMessage.fail(ErrorCodes.CAN_NOT_DESERIALIZE_REQUEST, e.getMessage());
        }
    }

    private void handleRequest(final Request request, final Message<JsonObject> requestMessage){
        final String methodToInvoke = request.getMethodToInvoke();
        handleMethodInvocation(requestMessage, methodToInvoke);
    }

    private void handleMethodInvocation(final Message<JsonObject> requestMessage, String methodToInvoke) {
        final Optional<MethodWithRouting> methodToInvokeOptional = findMethodToInvoke(methodToInvoke);

        if(methodToInvokeOptional.isPresent()) {
            final MethodWithRouting methodWithRouting = methodToInvokeOptional.get();
            final Method method = methodWithRouting.getMethod();
            final Object response = invokeMethod(method);
            requestMessage.reply(response);
        } else {
            requestMessage.fail(ErrorCodes.METHOD_NOT_FOUND, methodToInvoke + " not found");
        }
    }

    private Optional<MethodWithRouting> findMethodToInvoke(final String routing) {
        return methodWithRoutings.stream().filter(mwr -> mwr.getRouting().URL().equals(routing)).findFirst();
    }

    private Object invokeMethod(Method method) {
        try {
            return method.invoke(this);
        } catch (Exception e) {
            LOG.error("Error while invoking method", e);
        }

        return new Object();
    }
}
