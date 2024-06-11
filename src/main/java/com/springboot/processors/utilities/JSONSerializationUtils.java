package com.springboot.processors.utilities;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.processors.entities.SerializationExample;

public class JSONSerializationUtils {
	
	private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    // JSON Serialization
    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    // JSON Deserialization
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

	public static void main(String[] args) {
		try {
			SerializationExample obj = new SerializationExample("example", 123);
            String json = JSONSerializationUtils.toJson(obj);
            System.out.println("JSON: " + json);

            SerializationExample deserializedObj = JSONSerializationUtils.fromJson(json, SerializationExample.class);
            System.out.println("Deserialized Object: " + deserializedObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
