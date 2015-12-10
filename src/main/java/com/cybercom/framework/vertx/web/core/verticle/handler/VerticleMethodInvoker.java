package com.cybercom.framework.vertx.web.core.verticle.handler;

import com.cybercom.framework.vertx.web.core.error.ErrorCodes;
import com.cybercom.framework.vertx.web.core.scanner.method.MethodWithRouting;
import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import com.cybercom.framework.vertx.web.core.verticle.method.VerticleMethods;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.lang.reflect.Method;
import java.util.Optional;

public final class VerticleMethodInvoker implements Handler<Message<JsonObject>> {
    private static final Logger LOG = LoggerFactory.getLogger(VerticleMethodInvoker.class);

    private final Serializer serializer;
    private final VerticleMethods verticleMethods;
    private final Object parentClass;

    public VerticleMethodInvoker(final VerticleMethods verticleMethods, final Object parentClass) {
        this.serializer = new DefaultSerializer();
        this.verticleMethods = verticleMethods;
        this.parentClass = parentClass;
    }

    @Override
    public void handle(final Message<JsonObject> requestMessage) {
        try {
            final Request request = serializer.deserialize(requestMessage.body(), Request.class);
            handleRequest(request, requestMessage);
        } catch (SerializerException e) {
            LOG.error("Can not deserialize request", e);
            requestMessage.fail(ErrorCodes.CAN_NOT_DESERIALIZE_REQUEST, e.getMessage());
        }
    }

    private void handleRequest(final Request request, final Message<JsonObject> requestMessage){
        final Optional<MethodWithRouting> methodToInvokeOptional = findMethodToInvoke(request);

        if(methodToInvokeOptional.isPresent()) {
            final MethodWithRouting methodWithRouting = methodToInvokeOptional.get();
            invokeMethod(requestMessage, methodWithRouting);
        } else {
            requestMessage.fail(ErrorCodes.METHOD_NOT_FOUND, request.getMethodToInvoke() + " not found");
        }
    }

    private Optional<MethodWithRouting> findMethodToInvoke(final Request request) {
        final String methodToInvoke = request.getMethodToInvoke();

        return verticleMethods.findMethod(methodToInvoke);
    }

    private void invokeMethod(final Message<JsonObject> requestMessage, final MethodWithRouting methodWithRouting) {
        final Method method = methodWithRouting.getMethod();

        try {
            final Object response = method.invoke(parentClass);
            requestMessage.reply(response);
        } catch (Exception e) {
            LOG.error("Can not invoke method", e);
            requestMessage.fail(ErrorCodes.INVOCATION_EXCEPTION, e.getMessage());
        }
    }
}
