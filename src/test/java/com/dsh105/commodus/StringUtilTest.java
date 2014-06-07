package com.dsh105.commodus;

import junit.framework.Assert;
import org.junit.Test;

public class StringUtilTest {
    @Test
    public void testCapitalization() {
        Assert.assertEquals("Test Message", StringUtil.capitalise("test message"));
    }

    @Test
    public void testSentenceBuilder() {
        Assert.assertEquals("one, two and three", StringUtil.buildSentenceList("one", "two", "three"));
        Assert.assertEquals("one and two", StringUtil.buildSentenceList("one", "two"));
    }

    @Test
    public void testNumericExtraction() {
        Assert.assertEquals(100, GeneralUtil.numericValueOf("100"));
        Assert.assertEquals(100, GeneralUtil.numericValueOf("Example Number (100)"));
    }
}
