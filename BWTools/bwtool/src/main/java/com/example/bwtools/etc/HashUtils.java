package com.example.bwtools.etc;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtils {
	   public String getSHA512(String input){

	        String toReturn = null;
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-512");
	            digest.reset();
	            digest.update(input.getBytes(StandardCharsets.UTF_8));
	            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return toReturn;
	    }
	   
	   public static String getSHA256(String input){

			String toReturn = null;
			try {
			    MessageDigest digest = MessageDigest.getInstance("SHA-256");
			    digest.reset();
			    digest.update(input.getBytes(StandardCharsets.UTF_8));
			    toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
			return toReturn;
		    }
}
