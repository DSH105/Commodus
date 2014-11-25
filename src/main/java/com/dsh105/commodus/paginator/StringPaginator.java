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
import java.util.List;

/**
 * Represents a Paginator for an array of Strings
 */
public class StringPaginator extends ObjectPaginator<String> {

    public StringPaginator() {
        super();
    }

    public StringPaginator(int perPage) {
        super(perPage);
    }

    public StringPaginator(int perPage, String... raw) {
        super(perPage, raw);
    }

    public StringPaginator(List<String> raw, int perPage) {
        super(raw, perPage);
    }

    @Override
    public StringPaginator clone() throws CloneNotSupportedException {
        return (StringPaginator) super.clone();
    }
}