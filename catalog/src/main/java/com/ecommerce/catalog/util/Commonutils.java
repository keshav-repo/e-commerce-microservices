package com.ecommerce.catalog.util;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commonutils {
    public static boolean isPatternMatch(String text, String pattern) {
        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(text);
        return matcher.find();
    }

    public static String decode(String s){
        try {
            return URLDecoder.decode(s, "UTF-8");
        }catch (Exception e){
            throw new RuntimeException("Error decoding url");
        }
    }
}
