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

package com.dsh105.commodus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class GeneralUtil {

    private static Random RANDOM;

    public static Random random() {
        if (RANDOM == null) {
            RANDOM = new Random();
        }
        return RANDOM;
    }

    public static boolean isEnumType(Class<? extends Enum> clazz, String toTest) {
        try {
            Enum.valueOf(clazz, toTest.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Tests if the given String is an Integer
     *
     * @param toTest the String to be checked
     * @return true if Integer
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
     * @param toTest the String to be checked
     * @return true if Double
     */
    public static boolean isDouble(String toTest) {
        try {
            Double.parseDouble(toTest);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static int numericValueOf(String original) {
        return Integer.valueOf(original.replaceAll("[^0-9]", ""));
    }

    public static Location readLocation(int startIndex, String... args) {
        World world = Bukkit.getWorld(args[startIndex]);
        if (world == null) {
            throw new IllegalArgumentException("World does not exist!");
        }
        int[] coords = new int[3];
        int index = 0;
        for (int i = startIndex + 1; i < startIndex + 4; i++) {
            if (!isInt(args[i])) {
                return null;
            }
            coords[index++] = Integer.parseInt(args[i]);
        }
        return new Location(world, coords[0], coords[1], coords[2]);
    }
}