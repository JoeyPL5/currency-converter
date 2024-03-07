package com.Util;

public class StringUtil {

    /**
     * Takes in multiple Strings and combines them into a single, comma-separated String.
     * 
     * @param strings the Strings to combine
     * @return a single, comma-separated String
     */
    public static String commaSeparatedString(String... strings) {
        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            StringBuilder thisString = new StringBuilder(strings[i]);
            if (i != strings.length - 1) {
                thisString.append(',');
            }
            finalString.append(thisString);
        }
        return finalString.toString();
    }
}
