package com.springboot.processors.utilities;




import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringConversionUtil {

	private static String getPrettyPrintJson(String jsonString) {
		if (StringUtils.isNoneBlank(jsonString)) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Object json = mapper.readValue(jsonString, Object.class);
				String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
				jsonString = indented;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return jsonString;
	}
	
	private static String convertToNormalString(String jsonString) {
        if (StringUtils.isNoneBlank(jsonString)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Object json = mapper.readValue(jsonString, Object.class);
                jsonString = mapper.writeValueAsString(json);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return jsonString;
    }
	
	public static void main(String args[]) {
		String normalString1 = "{\"id\": \"1\", \"name\": \"John Doe\", \"phone\": \"123-456-7890\", \"city\": \"New York\"}";
		String jsonString1 = getPrettyPrintJson(normalString1);
		System.out.println(jsonString1);
		
		String jsonString2 = "{" +
	            "\"id\": \"1\"," +
	            "\"name\": \"John Doe\"," +
	            "\"phone\": \"123-456-7890\"," +
	            "\"city\": \"New York\"" +
	            "}";
		
		String normalString2 = convertToNormalString(jsonString2);
		System.out.println(normalString2);
	}
	
}
