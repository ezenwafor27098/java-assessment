package com.chargepoint.transaction.deserializer;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.chargepoint.transaction.model.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer<T> implements Deserializer<T> {

    private final Class<T> clazz;
    private final ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public CustomDeserializer() {
        this.clazz = (Class<T>) AuthenticationResponse.class;
        this.objectMapper = new ObjectMapper();
    }

    public CustomDeserializer(Class<T> clazz) {
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, clazz);
        } catch (IOException ex) {
            throw new SerializationException("Error deserializing JSON message", ex);
        } 
        
    }

}
