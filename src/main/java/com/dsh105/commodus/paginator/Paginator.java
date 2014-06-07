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

package com.dsh105.commodus.paginator;

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