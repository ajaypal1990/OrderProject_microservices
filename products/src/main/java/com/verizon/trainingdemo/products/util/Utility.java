package com.verizon.trainingdemo.products.util;

import java.util.Random;

import org.springframework.util.StringUtils;

public class Utility {
	
	public static boolean isEmpty(Object obj) {
		
		if ( obj == null || StringUtils.isEmpty(obj)) {
			return true;
		}
		return false;
	}
	
	
	
	public static String generateProductId() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	 
	    return generatedString;
	}
}
