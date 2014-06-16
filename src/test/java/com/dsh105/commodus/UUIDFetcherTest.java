package com.dsh105.commodus;

import junit.framework.Assert;
import org.junit.Test;

import java.util.UUID;

public class UUIDFetcherTest {
    @Test
    public void testFetching() throws Exception {
        Assert.assertEquals(UUID.fromString("2d762d9c-2a1a-4d99-9821-8f6b5e11a51a"), UUIDFetcher.getUUIDOf("stuntguy3000"));
    }

    @Test
    public void testCaching() {
        try {
            UUIDFetcher.getUUIDOf("Vexil_"); // Load "Vexil_" into the UUIDFetcher's Cache
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, UUIDFetcher.getCache().asMap().containsKey("Vexil_"));
    }
}
