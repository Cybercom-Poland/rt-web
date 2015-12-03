package com.cybercom.framework.vertx.web.core.serializer;

import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public final class DefaultSerializer implements Serializer{
    private final ObjectMapper mapper;

    public DefaultSerializer() {
        mapper = new ObjectMapper();
    }

    @Override
    public JsonObject serialize(final Object objectToSerialize) throws SerializerException {
        try {
            final String json = Json.encode(objectToSerialize);

            return new JsonObject(json);
        } catch (EncodeException e) {
            throw new SerializerException("Can not serialize", e);
        }
    }

    @Override
    public <T> T deserialize(final JsonObject jsonObject, final Class<T> type) throws SerializerException {
        final String json = jsonObject.toString();

        try {
            return Json.decodeValue(json, type);
        } catch (DecodeException e) {
            throw new SerializerException("Can not deserialize", e);
        }
    }
}
