package com.dsh105.simpleutils;

import junit.framework.Assert;
import org.junit.Test;

import java.util.UUID;

public class UUIDFetcherTest {
    @Test
    public void testFetching() throws Exception {
        Assert.assertEquals(UUID.fromString("2d762d9c-2a1a-4d99-9821-8f6b5e11a51a"), UUIDFetcher.getUUIDOf("stuntguy3000"));
    }
}
