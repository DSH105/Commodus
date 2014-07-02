/*
 * This file is part of Commodus.
 *
 * Commodus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Commodus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Commodus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.commodus;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.lang.IllegalArgumentException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String EMPTY = "";
    private static final Pattern DIACRITICS_AND_FRIENDS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    /**
     * Remove the specified ChatColors from a String
     *
     * @param input String to be sanitized
     * @param colorsToRemove ChatColors to be removed
     * @return sanitized input
     */
    public String removeColor(String input, ChatColor... colorsToRemove) {
        String result = input;
        for (ChatColor color : colorsToRemove) {
            input = input.replaceAll("(?i)" + ChatColor.COLOR_CHAR + color.getChar(), "");
        }
        return result;
    }

    /**
     * Convert a number of Objects into Strings
     *
     * @param arrayToConvert Objects to be converted
     * @return String[] Objects converted into Strings
     */
    public static String[] convert(Object... arrayToConvert) {
        if (arrayToConvert.length <= 0) {
            return new String[0];
        }
        String[] stringArray = new String[arrayToConvert.length];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = arrayToConvert[i].toString();
        }
        return stringArray;
    }

    /**
     * Capitalizes the first letter of a String
     *
     * @param string the String to be capitalized
     * @return capitalized String
     */
    public static String capitalise(String string) {
        String[] parts = string.split(" ");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
        }
        return combineArray(0, " ", parts);
    }

    public static String[] separate(int startIndex, String... string) {
        if (startIndex >= string.length || string.length <= 0) {
            return new String[0];
        }
        String[] str = new String[string.length - startIndex];
        for (int i = startIndex; i < string.length; i++) {
            str[i] = string[i];
        }
        return str;
    }

    /**
     * Builds a sentence list from an array of strings.
     * Example: {"one", "two", "three"} returns "one, two and three".
     *
     * @param words The string array to build into a list,
     * @return String representing the list.
     */
    public static String buildSentenceList(String... words) {
        Validate.notEmpty(words);
        if (words.length == 1) {
            return words[0];
        } else if (words.length == 2) {
            return combineArray(0, " and ", words);
        } else {
            // This is where the fun starts!
            String[] initial = Arrays.copyOfRange(words, 0, words.length - 1);
            String list = combineArray(0, ", ", initial);
            list += " and " + words[words.length - 1];
            return list;
        }
    }

    @Deprecated
    public static String combineSplit(int startIndex, String[] string, String separator) {
        return combineArray(startIndex, separator, string);
    }

    public static String combineArray(String separator, String... stringArray) {
        return combineArray(0, separator, stringArray);
    }

    public static String combineArray(int startIndex, String separator, String... stringArray) {
        if (stringArray == null || startIndex >= stringArray.length) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = startIndex; i < stringArray.length; i++) {
                builder.append(stringArray[i]);
                builder.append(separator);
            }
            builder.delete(builder.length() - separator.length(), builder.length());
            return builder.toString();
        }
    }

    public static String combine(String separator, Collection<String> stringCollection) {
        return combineArray(separator, stringCollection.toArray(EMPTY_STRING_ARRAY));
    }

    public static String combine(int startIndex, String separator, Collection<String> stringCollection) {
        return combineArray(startIndex, separator, stringCollection.toArray(EMPTY_STRING_ARRAY));
    }

    public static String[] splitArgs(int startIndex, String separator, String... stringArray) {
        String combined = combineArray(startIndex, separator, stringArray);
        if (combined.isEmpty()) {
            return new String[0];
        }
        return combined.split(separator);
    }

    // http://stackoverflow.com/a/1453284
    public static String stripDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }

    /**
     * Convert a String to a UUID
     * </p>
     * This method will add the required dashes, if not found in String
     *
     * @param input
     * @return UUID converted UUID
     * @throws IllegalArgumentException input could not be converted
     */
    public static UUID convertUUID(String input) throws IllegalArgumentException {
        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException ex) {
            return UUID.fromString(input.replaceAll(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                    "$1-$2-$3-$4-$5"));
        }
    }
}
