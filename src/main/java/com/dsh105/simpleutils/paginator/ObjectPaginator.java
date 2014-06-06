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

package com.dsh105.simpleutils.paginator;

import java.util.ArrayList;

/**
 * Represents a Paginated list that can be used to systematically separate entries of a certain item
 *
 * @param <T> Type of item to separate
 */
public class ObjectPaginator<T> {

    private ArrayList<T> raw = new ArrayList<>();
    private int perPage;

    /**
     * Construct a new Paginator from the given raw content
     *
     * @param perPage Amount of entries allowed per page
     * @param raw Raw content to paginate
     */
    public ObjectPaginator(int perPage, T... raw) {
        this.perPage = perPage;
        this.setRaw(raw);
    }

    /**
     * Construct a new Paginator from the given raw content
     *
     * @param raw Raw content to paginate
     * @param perPage Amount of entries allowed per page
     */
    public ObjectPaginator(ArrayList<T> raw, int perPage) {
        this.perPage = perPage;
        this.setRaw(raw);
    }

    /**
     * Sets new raw content
     *
     * @param raw Raw content to be paginated
     */
    public void setRaw(T... raw) {
        for (T r : raw) {
            this.raw.add(r);
        }
    }

    /**
     * Sets new raw content
     *
     * @param raw Raw content to be paginated
     */
    public void setRaw(ArrayList<T> raw) {
        this.raw = raw;
    }

    /**
     * Gets raw content to be paginated
     *
     * @return Raw content to be paginated
     */
    public ArrayList<T> getRaw() {
        return this.raw;
    }

    /**
     * Gets a converted list of all raw content
     *
     * @return Converted list of raw content
     */
    public ArrayList<String> getRawContent() {
        ArrayList<String> rawContent = new ArrayList<>();
        for (T raw : this.raw) {
            rawContent.add(getConvertedContent(raw));
        }
        return rawContent;
    }

    /**
     * Gets the total page index of the paginated content
     *
     * @return Total page index
     */
    public int getIndex() {
        return (int) getIndexAsDouble();
    }

    /**
     * Gets the full total page index of the paginated content as a Double
     *
     * @return Total page index as a double
     */
    public double getIndexAsDouble() {
        return (Math.ceil(this.raw.size() / ((double) this.perPage)));
    }

    /**
     * Gets a certain page of content by page number
     *
     * @param pageNumber Page number to retrieve content for
     * @return Content of a certain page
     */
    public String[] getPage(int pageNumber) {
        return this.getPage(pageNumber, perPage);
    }

    /**
     * Gets a certain page of content by page number
     *
     * @param pageNumber Page number to retrieve content for
     * @param perPage Amount of entries allowed per page
     * @return Content of a certain page
     */
    public String[] getPage(int pageNumber, int perPage) {
        return getPage(pageNumber, perPage, false);
    }

    /**
     * Gets a certain page of raw content by page number
     *
     * @param pageNumber Page number to retrieve raw content for
     * @param perPage Amount of entries allowed per page
     * @return Raw content of a certain page
     */
    public ArrayList<T> getRawPage(int pageNumber, int perPage) {
        if (pageNumber > getIndexAsDouble()) {
            throw new IllegalArgumentException("Page does not exist!");
        }
        int index = perPage * (Math.abs(pageNumber) - 1);
        ArrayList<T> list = new ArrayList<>();
        for (int i = index; i < (index + perPage); i++) {
            if (this.raw.size() > i) {
                list.add(raw.get(i));
            }
        }
        return list;
    }

    public String[] getPage(int pageNumber, int perPage, boolean raw) {
        if (pageNumber > getIndexAsDouble()) {
            throw new IllegalArgumentException("Page does not exist!");
        }
        int index = perPage * (Math.abs(pageNumber) - 1);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = index; i < (index + perPage); i++) {
            if (this.raw.size() > i) {
                list.add(getConvertedContent(this.raw.get(i)));
            }
        }
        return list.toArray(new String[list.size()]);
    }

    protected String getConvertedContent(T rawObject) {
        return rawObject.toString();
    }
}