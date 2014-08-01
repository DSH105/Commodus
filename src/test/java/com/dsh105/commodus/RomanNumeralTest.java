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

import junit.framework.Assert;
import org.junit.Test;

public class RomanNumeralTest {

    private static int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    @Test
    public void testRomanNumeralIntegerConversion() {
        int count = 0;
        for (int num : numbers) {
            RomanNumeral romanNumeral = new RomanNumeral(num);

            Assert.assertEquals(letters[count], romanNumeral.toString());
            count++;
        }
    }

    @Test
    public void testRomanNumeralStringConversion() {
        int count = 0;
        for (String letter : letters) {
            RomanNumeral romanNumeral = new RomanNumeral(letter);

            Assert.assertEquals(numbers[count], romanNumeral.toInt());
            count++;
        }
    }

    @Test
    public void testMultiStringConversions() {
        String text = "CMLXXX";
        RomanNumeral romanNumeral = new RomanNumeral(text);
        Assert.assertEquals(980, romanNumeral.toInt());

        text = "LXXX";
        romanNumeral = new RomanNumeral(text);
        Assert.assertEquals(80, romanNumeral.toInt());

        text = "LXXIV";
        romanNumeral = new RomanNumeral(text);
        Assert.assertEquals(74, romanNumeral.toInt());

        text = "CCCLXXXV";
        romanNumeral = new RomanNumeral(text);
        Assert.assertEquals(385, romanNumeral.toInt());

        text = "CXXVI";
        romanNumeral = new RomanNumeral(text);
        Assert.assertEquals(126, romanNumeral.toInt());
    }

    @Test
    public void testNumberConversions() {
        int value = 980;
        RomanNumeral romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("CMLXXX", romanNumeral.toString());

        value = 80;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("LXXX", romanNumeral.toString());

        value = 74;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("LXXIV", romanNumeral.toString());

        value = 385;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("CCCLXXXV", romanNumeral.toString());

        value = 126;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("CXXVI", romanNumeral.toString());
    }
}
