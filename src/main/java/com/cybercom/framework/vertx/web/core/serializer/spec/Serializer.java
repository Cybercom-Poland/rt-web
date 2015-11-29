package com.cybercom.framework.vertx.web.core.serializer.spec;

import com.cybercom.framework.vertx.web.core.serializer.SerializerException;
import io.vertx.core.json.JsonObject;

public interface Serializer {
    JsonObject serialize(final Object objectToSerialize) throws SerializerException;
    <T> T deserialize(final JsonObject jsonObject, final Class<T> type) throws SerializerException;
}
