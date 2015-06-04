package org.codechimp.androidutils;

public class StringUtils {

    /**
     * Convert A String To Title Case where every new word is capitalized
     *
     * @param inputString - input string
     * @return inputString converted to Title Case
     */
    public static String toTitleCase(String inputString) {
        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : inputString.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }
}
