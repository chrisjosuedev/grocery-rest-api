package dev.chrisjosue.groceryrestapi.helpers.patterns;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class MyUtils {
    public static boolean matchWithString(String value, String regex) {
        Pattern special = Pattern.compile(regex);
        return special.matcher(value).find();
    }

    public static Double round(double number, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(number);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
