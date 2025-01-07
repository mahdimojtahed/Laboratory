package com.mehdi.Laboratory.shared.utils;

public class StringUtils {

    public static String defaultIfEmpty(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

}
