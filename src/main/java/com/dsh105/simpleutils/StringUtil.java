/*
 * This file is part of SimpleUtils.
 *
 * SimpleUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SimpleUtils.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.simpleutils;

import org.apache.commons.lang.Validate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtil {

    private static final String EMPTY = "";

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
        if (string == null || startIndex >= string.length) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = startIndex; i < string.length; i++) {
                builder.append(string[i]);
                builder.append(separator);
            }
            builder.delete(builder.length() - separator.length(), builder.length());
            return builder.toString();
        }
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

    public static String[] splitArgs(int startIndex, String separator, String... stringArray) {
        String combined = combineArray(startIndex, separator, stringArray);
        if (combined.isEmpty()) {
            return new String[0];
        }
        return combined.split(separator);
    }

    /**
     * Joins the elements of an iterator together separated by the given separator.
     *
     * @param iterator
     * @param separator
     * @return
     */
    public static String join(Iterator iterator, char separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first.toString();
        }

        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

    /**
     * Joins the elements of an iterator together separated by the given separator.
     *
     * @param iterator
     * @param separator
     * @return
     */
    public static String join(Iterator iterator, String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first.toString();
        }

        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * Joins the elements of a collection together separated by the given separator.
     *
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection collection, char separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    /**
     * Joins the elements of a collection together separated by the given separator.
     *
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    /**
     * Joins the elements of an array together separated by the given separator.
     *
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(Arrays.asList(array), separator);
    }

    /**
     * Joins the elements of an array together separated by the given separator.
     *
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(Arrays.asList(array), separator);
    }
}
