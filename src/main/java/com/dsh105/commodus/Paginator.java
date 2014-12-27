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

package com.dsh105.commodus;

import java.util.*;

/**
 * Generates a paginated list of items separated into an arbitrary number of sets of equal length.
 *
 * @param <T> accepted type of item that can be paginated
 */
public class Paginator<T> implements Iterable<T>, Cloneable {

    // TODO: docs

    private List<T> content = new ArrayList<>();
    private int perPage;

    public Paginator() {
    }

    public Paginator(int perPage) {
        this.perPage = perPage;
    }

    public Paginator(int perPage, T[] content) {
        this.perPage = perPage;
        this.append(content);
    }

    public Paginator(int perPage, Collection<T> content) {
        this.perPage = perPage;
        this.append(content);
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void append(Collection<T> entries) {
        Affirm.notNull(entries);
        this.content.addAll(entries);
    }

    public void append(T[] entries) {
        Affirm.notEmpty(entries);
        Collections.addAll(this.content, entries);
    }

    public void append(T entry) {
        this.content.add(entry);
    }

    public void remove(Collection<T> entries) {
        Affirm.notNull(entries);
        for (T entry : entries) {
            remove(entry);
        }
    }

    public void remove(T[] entries) {
        Affirm.notEmpty(entries);
        for (T entry : entries) {
            remove(entry);
        }
    }

    public void remove(T entry) {
        this.content.remove(entry);
    }

    public void clear() {
        this.content.clear();
    }

    public int getTotalPages() {
        return (int) (Math.ceil(content.size() / ((double) perPage)));
    }

    public boolean exists(int pageNumber) {
        return exists(pageNumber, perPage);
    }

    public boolean exists(int pageNumber, int perPage) {
        try {
            getPage(pageNumber, perPage);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<T> getPage(int pageNumber) {
        return getPage(pageNumber, perPage);
    }

    public List<T> getPage(int pageNumber, int perPage) {
        if (pageNumber <= 0 || pageNumber > getTotalPages()) {
            throw new IllegalArgumentException("Page " + pageNumber + " does not exist.");
        }

        int index = perPage * (Math.abs(pageNumber) - 1);
        List<T> page = new ArrayList<>();
        for (int i = index; i < (index + perPage); i++) {
            if (content.size() > i) {
                page.add(content.get(i));
            }
        }
        return Collections.unmodifiableList(page);
    }

    public List<String> getConvertedPage(int pageNumber) {
        return getConvertedPage(pageNumber, perPage);
    }

    public List<String> getConvertedPage(int pageNumber, int perPage) {
        List<T> page = getPage(pageNumber, perPage);
        return GeneralUtil.transform(page, new Transformer<T, String>() {
            @Override
            public String transform(T transmutable) {
                return transmutable.toString();
            }
        });
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public Paginator<T> clone() throws CloneNotSupportedException {
        Paginator<T> clone = (Paginator<T>) super.clone();
        clone.content = new ArrayList<>(this.content);
        return clone;
    }
}