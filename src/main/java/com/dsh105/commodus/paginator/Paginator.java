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

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Paginator for {@link Pageable} objects
 *
 * @param <T> Type of item to separate that must be {@link Pageable}
 */
public class Paginator<T extends Pageable> extends ObjectPaginator<T> {

    public Paginator() {
        super();
    }

    public Paginator(int perPage) {
        super(perPage);
    }

    public Paginator(int perPage, T... raw) {
        super(perPage, raw);
    }

    public Paginator(List<T> raw, int perPage) {
        super(raw, perPage);
    }

    public void sendPage(CommandSender sender, int pageNumber) {
        sendPage(sender, pageNumber, getPerPage());
    }

    public void sendPage(CommandSender sender, int pageNumber, int perPage) {
        sendPage(sender, pageNumber, perPage, false);
    }

    public void sendPage(CommandSender sender, int pageNumber, int perPage, boolean raw) {
        for (T pageable : getRawPage(pageNumber, perPage)) {
            if (raw) {
                sender.sendMessage(pageable.toString());
            } else {
                pageable.send(sender);
            }
        }
    }

    @Override
    protected String getConvertedContent(T rawObject) {
        return rawObject.getContent();
    }

    @Override
    public Paginator<T> clone() throws CloneNotSupportedException {
        return (Paginator<T>) super.clone();
    }
}