package com.example.bwtools.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberShipUtils {
	  public static boolean CheckID(String id){
	        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(id);

	        boolean isNormal = m.matches();
	        return isNormal;
	    }

	    public static boolean isEmailValid(String email) {
	        Pattern pattern;
	        Matcher matcher;
	        final String EMAIL_PATTERN =
	                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	        pattern = Pattern.compile(EMAIL_PATTERN);
	        matcher = pattern.matcher(email);
	        return matcher.matches();
	    }
	    
	    //가장 많이 사용되는 최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
	    public static boolean CheckPwd(String pwd){
	        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}$");
	        Matcher m = p.matcher(pwd);

	        if(m.matches()){
	            return true;
	        }
	        return false;

	    }
}
