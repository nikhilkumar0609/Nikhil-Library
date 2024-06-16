package com.springboot.processors.utilities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.processors.entities.SerializationExample;

public class XMLSerializationUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    // XML Serialization
    public static String toXml(Object obj) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, sw);
        return sw.toString();
    }

    // XML Deserialization
    public static <T> T fromXml(String xml, Class<T> clazz) throws JAXBException {
        StringReader sr = new StringReader(xml);
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(sr);
    }
	
    
    public static void main(String[] args) {
        try {
        	SerializationExample obj = new SerializationExample("example", 123);
            String xml = XMLSerializationUtils.toXml(obj);
            System.out.println("XML: " + xml);

            SerializationExample deserializedObj = XMLSerializationUtils.fromXml(xml, SerializationExample.class);
            System.out.println("Deserialized Object: " + deserializedObj);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
