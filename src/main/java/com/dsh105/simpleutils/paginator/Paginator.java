package com.dsh105.simpleutils.paginator;

import java.util.ArrayList;

/**
 * Represents a Paginator for {@link Pageable} objects
 *
 * @param <T> Type of item to separate that must be {@link Pageable}
 */
public class Paginator<T extends Pageable> extends ObjectPaginator<T> {

    public Paginator(int perPage, T... raw) {
        super(perPage, raw);
    }

    public Paginator(ArrayList<T> raw, int perPage) {
        super(raw, perPage);
    }

    @Override
    protected String getConvertedContent(T rawObject) {
        return rawObject.getContent();
    }
}