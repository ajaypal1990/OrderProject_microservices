package com.verizon.trainingdemo.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsontoObjectMapperUtil {

	private static ObjectMapper mapper = new ObjectMapper();
	public static String toJson(Object object ) throws JsonProcessingException {
		
		return mapper.writeValueAsString(object);
	}
	public static Object toObject( Class cls , String data  ) throws JsonMappingException, JsonProcessingException {
		
		return mapper.readValue(data, cls);
	}
	
	
}
