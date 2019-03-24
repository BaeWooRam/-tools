package com.example.bwtools.common;

public class PayUtils {
	   public static int CheckPrice(String str_price){
	        int p;
	        if(str_price != null)
	            p =Integer.parseInt(str_price);
	        else
	            p = 0;
	
	        return p;
	    }	
}

