package com.dsh105.simpleutils.paginator;

import java.util.ArrayList;

/**
 * Represents a Paginator for an array of Strings
 */
public class StringPaginator extends ObjectPaginator<String> {

    public StringPaginator(int perPage, String... raw) {
        super(perPage, raw);
    }

    public StringPaginator(ArrayList<String> raw, int perPage) {
        super(raw, perPage);
    }
}