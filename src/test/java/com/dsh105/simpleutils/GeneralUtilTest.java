package com.dsh105.simpleutils;

import junit.framework.Assert;
import org.junit.Test;

public class GeneralUtilTest {
    @Test
    public void testIntegerValidation() {
        Assert.assertEquals(true, GeneralUtil.isInt("100"));
        Assert.assertEquals(false, GeneralUtil.isInt("NotANumber"));
    }

    @Test
    public void testDoubleValidation() {
        Assert.assertEquals(true, GeneralUtil.isDouble("100.5"));
        Assert.assertEquals(false, GeneralUtil.isDouble("NotANumber"));
    }

    @Test
    public void testNumericExtraction() {
        Assert.assertEquals(100, GeneralUtil.numericValueOf("100"));
        Assert.assertEquals(100, GeneralUtil.numericValueOf("Example Number (100)"));
    }
}
