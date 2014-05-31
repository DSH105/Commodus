package com.dsh105.simpleutils.paginator;

/**
 * Represents a pageable object that can be used in a {@link com.dsh105.simpleutils.paginator.Paginator}
 */
public interface Pageable {

    /**
     * Gets the content to paginate for this object
     *
     * @return Content to paginate
     */
    public String getContent();
}