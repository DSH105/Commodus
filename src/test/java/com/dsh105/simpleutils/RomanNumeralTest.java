package com.dsh105.simpleutils;

import junit.framework.Assert;
import org.junit.Test;

public class RomanNumeralTest {
    private static int[] numbers = {1000, 900, 500, 400, 100,  90, 50, 40, 10, 9, 5, 4, 1};
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
        Assert.assertEquals("CMLXXX", romanNumeral.toInt());

        value = 80;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("LXXX", romanNumeral.toInt());

        value = 74;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("LXXIV", romanNumeral.toInt());

        value = 385;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("CCCLXXXV", romanNumeral.toInt());

        value = 126;
        romanNumeral = new RomanNumeral(value);
        Assert.assertEquals("CXXVI", romanNumeral.toInt());
    }
}
