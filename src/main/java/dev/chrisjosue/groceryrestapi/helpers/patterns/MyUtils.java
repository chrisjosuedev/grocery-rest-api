package dev.chrisjosue.groceryrestapi.helpers.patterns;

import java.util.regex.Pattern;

public class MyUtils {
    public static boolean matchWithString(String value, String regex) {
        Pattern special = Pattern.compile(regex);
        return special.matcher(value).find();
    }
}
