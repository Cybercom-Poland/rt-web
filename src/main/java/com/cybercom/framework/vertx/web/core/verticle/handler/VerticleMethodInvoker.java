package com.cybercom.framework.vertx.web.core.verticle.handler;

import com.cybercom.framework.vertx.web.core.error.ErrorCodes;
import com.cybercom.framework.vertx.web.core.serializer.DefaultSerializer;
import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.cybercom.framework.vertx.web.core.server.http.request.Request;
import com.cybercom.framework.vertx.web.core.verticle.method.ExecutableMethod;
import com.cybercom.framework.vertx.web.core.verticle.method.VerticleMethods;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
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
        final Optional<ExecutableMethod> executableMethodOptiona = findMethodToInvoke(request);

        if(executableMethodOptiona.isPresent()) {
            final ExecutableMethod executableMethod = executableMethodOptiona.get();
            invokeMethod(requestMessage, executableMethod);
        } else {
            requestMessage.fail(ErrorCodes.METHOD_NOT_FOUND, request.getMethodToInvoke() + " not found");
        }
    }

    private Optional<ExecutableMethod> findMethodToInvoke(final Request request) {
        return verticleMethods.findMethod(request);
    }

    private void invokeMethod(final Message<JsonObject> requestMessage, final ExecutableMethod executableMethod) {
        try {
            final Object response = executableMethod.invoke(parentClass);
            requestMessage.reply(response);
        } catch (Exception e) {
            LOG.error("Can not invoke method", e);
            requestMessage.fail(ErrorCodes.INVOCATION_EXCEPTION, e.getMessage());
        }
    }
}
