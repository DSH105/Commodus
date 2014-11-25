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

package com.dsh105.commodus.paginator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a Paginated list that can be used to systematically separate entries of a certain item
 *
 * @param <T> Type of item to separate
 */
public class ObjectPaginator<T> implements Iterable<T>, Cloneable {

    private List<T> raw = new ArrayList<>();
    private int perPage;

    /**
     * Construct a new, empty Paginator
     */
    public ObjectPaginator() {
    }

    /**
     * Construct a new, empty Paginator
     *
     * @param perPage Amount of entries allowed per page
     */
    public ObjectPaginator(int perPage) {
        this.perPage = perPage;
    }

    /**
     * Construct a new Paginator from the given raw content
     *
     * @param perPage Amount of entries allowed per page
     * @param raw     Raw content to paginate
     */
    public ObjectPaginator(int perPage, T... raw) {
        this.perPage = perPage;
        this.add(raw);
    }

    /**
     * Construct a new Paginator from the given raw content
     *
     * @param raw     Raw content to paginate
     * @param perPage Amount of entries allowed per page
     */
    public ObjectPaginator(List<T> raw, int perPage) {
        this.perPage = perPage;
        this.raw = raw;
    }

    /**
     * Gets how many entries are included per page
     *
     * @return Entries included per page
     */
    public int getPerPage() {
        return perPage;
    }

    /**
     * Sets how many entries are included per page
     *
     * @param perPage Entries included per page
     */
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    /**
     * Adds new raw content
     *
     * @param raw Raw content to be paginated
     */
    public void add(T... raw) {
        Collections.addAll(this.raw, raw);
    }

    public boolean remove(T raw) {
        return this.raw.remove(raw);
    }

    public void set(int index, T content) {
        if (index >= raw.size()) {
            throw new IllegalArgumentException("Content at index does not exist.");
        }
        this.raw.set(index, content);
    }

    /**
     * Clears all content
     */
    public void clear() {
        this.raw.clear();
    }

    /**
     * Sets new raw content
     *
     * @param raw Raw content to be paginated
     */
    public void setRaw(List<T> raw) {
        this.raw = raw;
    }

    /**
     * Gets raw content to be paginated
     *
     * @return Raw content to be paginated
     */
    public List<T> getRaw() {
        return Collections.unmodifiableList(this.raw);
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
    public int getPages() {
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
     * Gets whether or not a certain page exists
     *
     * @param pageNumber Page number to retrieve content for
     * @return True if the page exists
     */
    public boolean exists(int pageNumber) {
        return exists(pageNumber, perPage);
    }

    /**
     * Gets whether or not a certain page exists
     *
     * @param pageNumber Page number to retrieve content for
     * @param perPage    Amount of entries allowed per page
     * @return True if the page exists
     */
    public boolean exists(int pageNumber, int perPage) {
        try {
            getPage(pageNumber, perPage);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
     * @param perPage    Amount of entries allowed per page
     * @return Content of a certain page
     */
    public String[] getPage(int pageNumber, int perPage) {
        if (pageNumber > getIndexAsDouble()) {
            throw new IllegalArgumentException("Page does not exist!");
        }
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page cannot be less than (or equal to) 0!");
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

    /**
     * Gets a certain page of raw content by page number
     *
     * @param pageNumber Page number to retrieve raw content for
     * @return Raw content of a certain page
     */
    public ArrayList<T> getRawPage(int pageNumber) {
        return this.getRawPage(pageNumber, perPage);
    }

    /**
     * Gets a certain page of raw content by page number
     *
     * @param pageNumber Page number to retrieve raw content for
     * @param perPage    Amount of entries allowed per page
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

    protected String getConvertedContent(T rawObject) {
        return rawObject.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return getRaw().iterator();
    }

    @Override
    public ObjectPaginator<T> clone() throws CloneNotSupportedException {
        ObjectPaginator<T> paginator = (ObjectPaginator<T>) super.clone();
        paginator.raw = this.raw;
        return paginator;
    }
}