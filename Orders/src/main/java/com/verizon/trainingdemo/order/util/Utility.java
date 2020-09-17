package com.verizon.trainingdemo.order.util;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;    
public class Utility {

	public static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		return dtf.format(now);
		  
	}   
	
	public static String getDate1() {
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();
		    return formatter.format(date);
		  
	}
	public static boolean isEmpty(Object obj) {
		
		if ( obj == null || StringUtils.isEmpty(obj)) {
			return true;
		}
		return false;
	}
}

