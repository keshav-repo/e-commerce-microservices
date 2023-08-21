package com.ecommerce.catalog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commonutils {
    public static boolean isPatternMatch(String text, String pattern) {
        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(text);
        return matcher.find();
    }
}
