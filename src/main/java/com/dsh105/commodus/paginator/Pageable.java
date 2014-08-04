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

/**
 * Represents a pageable object that can be used in a {@link com.dsh105.commodus.paginator.Paginator}
 */
public interface Pageable {

    /**
     * Gets the content to paginate for this object
     *
     * @return Content to paginate
     */
    public String getContent();

    /**
     * Sends this content to a {@link org.bukkit.command.CommandSender}
     *
     * @param sender Whom to send the content to
     *               @return this object
     */
    public Pageable send(CommandSender sender);
}