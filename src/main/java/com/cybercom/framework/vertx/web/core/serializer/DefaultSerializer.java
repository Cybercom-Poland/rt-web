package com.cybercom.framework.vertx.web.core.serializer;

import com.cybercom.framework.vertx.web.core.serializer.spec.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import java.io.IOException;

public final class DefaultSerializer implements Serializer{
    private final ObjectMapper mapper;

    public DefaultSerializer() {
        mapper = new ObjectMapper();
    }

    @Override
    public JsonObject serialize(final Object objectToSerialize) throws SerializerException {
        try {
            final String json = mapper.writeValueAsString(objectToSerialize);

            return new JsonObject(json);
        } catch (JsonProcessingException e) {
            throw new SerializerException("Can not serialize", e);
        }
    }

    @Override
    public <T> T deserialize(final JsonObject jsonObject, final Class<T> type) throws SerializerException {
        final String json = jsonObject.toString();

        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new SerializerException("Can not deserialize", e);
        }
    }
}
