package com.meteor.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrFormatter {
    private static final String source = "\\{\\}";

    public static String format(String format, Object... arguments) {

        Pattern p = Pattern.compile(source);
        Matcher m = p.matcher(format);

        int i = 0; // arguments
        while (m.find() && i < arguments.length) {
            format = format.replaceFirst(source, String.valueOf(arguments[i++]));
        }
        return format;
    }
}
