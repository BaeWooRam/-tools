package com.example.bwtools.android;

public class PayUtils {
	   public static int CheckPrice(String price){
	        int p;
	        if(price != null)
	            p =Integer.parseInt(price);
	        else
	            p = 0;

	        return p;
	    }

}
