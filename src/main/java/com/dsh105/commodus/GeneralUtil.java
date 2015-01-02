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

import com.dsh105.commodus.container.PositionContainer;
import com.dsh105.commodus.sponge.SpongeUtil;
import com.google.common.collect.BiMap;
import org.bukkit.Bukkit;

import java.lang.reflect.Array;
import java.util.*;

/**
 * General utilities
 */
public class GeneralUtil {

    private static Random RANDOM;

    private GeneralUtil() {
    }

    /**
     * Returns a random :D
     *
     * @return A random something
     */
    public static Random random() {
        if (RANDOM == null) {
            RANDOM = new Random();
        }
        return RANDOM;
    }

    /**
     * Tests whether or not the given String is an value in a particular Enum
     * <p/>
     * {@code nameValue} is converted to upper case before being tested
     *
     * @param clazz     Enum to test
     * @param nameValue String value to test
     * @return True if the given String belongs to the Enum provided
     */
    public static boolean isEnumType(Class<? extends Enum> clazz, String nameValue) {
        return toEnumType(clazz, nameValue) != null;
    }

    /**
     * Converts the given String to an Enum constant
     * <p/>
     * {@code nameValue} is converted to upper case before being converted
     *
     * @param clazz     Enum to convert to
     * @param nameValue String value to convert
     * @param <T>       Enum type
     * @return An Enum constant belonging to the provided Enum class, or null if the given String does not belong to the
     * enum provided
     */
    public static <T extends Enum<T>> T toEnumType(Class<T> clazz, String nameValue) {
        try {
            return Enum.valueOf(clazz, nameValue.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Tests if the given String is an Integer
     *
     * @param toTest The String to be checked
     * @return True if Integer
     */
    public static boolean isInt(String toTest) {
        try {
            Integer.parseInt(toTest);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Tests if the given String is an Double
     *
     * @param toTest The String to be checked
     * @return True if Double
     */
    public static boolean isDouble(String toTest) {
        try {
            Double.parseDouble(toTest);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Gets the key at the specified value in a key-value map
     * <p/>
     * <strong>This does NOT take into account the existence of multiple keys with the same value</strong>
     *
     * @param map   Key-value map to search in
     * @param value Value to retrieve the key for
     * @param <K>   The type of keys maintained by the given map
     * @param <V>   The type of mapped values
     * @return Key mapping the given key, or null if not found
     */
    public static <K, V> K getKeyAtValue(Map<K, V> map, V value) {
        if (map instanceof BiMap) {
            return ((BiMap<K, V>) map).inverse().get(value);
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Inverts the given map from key-value mappings to value-key mappings
     * <p/>
     * Note: this may have unintended results if a certain value is included in the map more than once
     *
     * @param map Map to invert
     * @param <V> The type of keys maintained by the given map
     * @param <K> The type of mapped values
     * @return Inverted map
     */
    public static <V, K> Map<V, K> invertMap(Map<K, V> map) {
        Map<V, K> inverted = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }
        return inverted;
    }

    /**
     * Attempts to convert a string into an integer value using Regex
     *
     * @param string The String to be checked
     * @return The converted integer
     * @throws java.lang.NumberFormatException if the conversion failed
     */
    public static int toInteger(String string) throws NumberFormatException {
        try {
            return Integer.parseInt(string.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(string + " isn't a number!");
        }
    }

    /**
     * Attempts to convert a string into an double value using Regex
     *
     * @param string The String to be checked
     * @return The converted double
     * @throws java.lang.NumberFormatException if the conversion failed
     */
    public static double toDouble(String string) {
        try {
            return Double.parseDouble(string.replaceAll(".*?([\\d.]+).*", "$1"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(string + " isn't a number!");
        }
    }

    /**
     * Transforms an integer into a set of digits
     *
     * @param number Integer to transform
     * @return A set of digits representing the original number
     */
    public static int[] toDigits(int number) {
        ArrayList<Integer> digitsList = new ArrayList<>();
        if (number == 0) {
            return new int[]{0};
        }
        while (number != 0) {
            digitsList.add(number % 10);
            number /= 10;
        }

        int[] digits = new int[digitsList.size()];
        for (int i = digitsList.size() - 1; i >= 0; i--) {
            digits[i] = digitsList.get(digitsList.size() - i);
        }
        return digits;
    }

    /**
     * Merges a set of arrays into a single list, maintaining original order
     *
     * @param arrays Set of arrays to combine into a list
     * @param <T>    Type of object included in the set of arrays
     * @return A combined list including all original objects in the given arrays
     */
    public static <T> List<T> merge(T[]... arrays) {
        ArrayList<T> merged = new ArrayList<>();
        for (T[] array : arrays) {
            Collections.addAll(merged, array);
        }
        return merged;
    }

    /**
     * Transforms a given array to a new array of given type using the supplied Transformer
     *
     * @param to          Type to convert the array to
     * @param array       Array to convert to the given type, {@code to}
     * @param transformer Transformer utilised to convert each object of the given array to the new type
     * @param <O>         The object type to be transformed
     * @param <V>         The type of the resultant array
     * @return A new array of the type supplied by {@code to}
     */
    public static <O, V> V[] transform(Class<V> to, O[] array, Transformer<O, V> transformer) {
        Affirm.notEmpty(array);
        V[] result = (V[]) Array.newInstance(to, array.length);
        for (int i = 0; i < array.length; i++) {
            result[i] = transformer.transform(array[i]);
        }
        return result;
    }

    /**
     * Transforms a given list to a new list of given type using the supplied Transformer.
     *
     * @param list        List to convert to the given type, {@code V}
     * @param transformer Transformer utilised to convert each object of the given array to the new type
     * @param <O>         The object type to be transformed
     * @param <V>         The type of the resultant list
     * @return A new list of the type supplied by {@code V}
     */
    public static <O, V> List<V> transform(List<O> list, Transformer<O, V> transformer) {
        return transform(true, list, transformer);
    }

    /**
     * Transforms a given list to a new list of given type using the supplied Transformer.
     *
     * @param includeNull If set to true, null objects will be included in the resultant list
     * @param list        List to convert to the given type, {@code V}
     * @param transformer Transformer utilised to convert each object of the given array to the new type
     * @param <O>         The object type to be transformed
     * @param <V>         The type of the resultant list
     * @return A new list of the type supplied by {@code V}
     */
    public static <O, V> List<V> transform(boolean includeNull, List<O> list, Transformer<O, V> transformer) {
        return transform(includeNull, list, new ArrayList<V>(), transformer);
    }

    /**
     * Transforms a given collection to a new collection of given type using the supplied Transformer.
     *
     * @param collection  Collection to convert to the given type, {@code V}
     * @param to          Collection to insert the transformed values into
     * @param transformer Transformer utilised to convert each object of the given array to the new type
     * @param <O>         The object type to be transformed
     * @param <V>         The type of the resultant list
     * @return A new collection of the type supplied by {@code V}
     */
    public static <O, V, C extends Collection<O>, D extends Collection<V>> D transform(C collection, D to, Transformer<O, V> transformer) {
        return transform(true, collection, to, transformer);
    }

    /**
     * Transforms a given collection to a new provided collection using the supplied Transformer.
     *
     * @param includeNull If set to true, null objects will be included in the resultant list
     * @param collection  Collection to convert to the given type, {@code V}
     * @param to          Collection to insert the transformed values into
     * @param transformer Transformer utilised to convert each object of the given array to the new type
     * @param <O>         The object type to be transformed
     * @param <V>         The type of the resultant list
     * @return A new list of the type supplied by {@code V}
     */
    public static <O, V, C extends Collection<O>, D extends Collection<V>> D transform(boolean includeNull, C collection, D to, Transformer<O, V> transformer) {
        Affirm.notEmpty(collection);
        Affirm.notEmpty(to);
        for (O element : collection) {
            if (includeNull || element != null) {
                to.add(transformer.transform(element));
            }
        }
        return to;
    }

    /**
     * Parses a location from a set of String arguments
     * <p/>
     * Arguments must be in the following order:
     * <ul>
     *     <li>World name</li>
     *     <li>X coordinate</li>
     *     <li>Y coordinate</li>
     *     <li>Z coordinate</li
     *     <li><b>Optional: </b>Yaw</li>
     *     <li><b>Optional: </b>Pitch</li>
     * </ul>
     *
     * @param startIndex Index to start parsing location from
     * @param args       String arguments to parse location from
     * @return A Location built from the given args, or null if it could not be parsed
     * @throws java.lang.IllegalStateException if the world at {@code args[startIndex]} does not exist
     */
    public static PositionContainer readLocation(int startIndex, String... args) {
        UUID worldUid = null;
        switch (ServerUtil.getServerBrand().getCapsule()) {
            case BUKKIT:
                worldUid = Bukkit.getWorld(args[startIndex]).getUID();
            case SPONGE:
                worldUid = SpongeUtil.getGame().getServer().get().getWorld(args[startIndex]).get().getUniqueID();
        }
        if (worldUid == null) {
            throw new IllegalStateException("World does not exist!");
        }
        double[] coords = new double[5];
        for (int i = startIndex + 1, index = 0; i < startIndex + 6; i++, index++) {
            try {
                coords[index] = GeneralUtil.toDouble(args[i]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                if (i <= startIndex + 3) {
                    // coords MUST exist
                    return null;
                }
            }
        }

        return new PositionContainer(worldUid, coords[0], coords[1], coords[2], (float) coords[3], (float) coords[4]);
    }
}
