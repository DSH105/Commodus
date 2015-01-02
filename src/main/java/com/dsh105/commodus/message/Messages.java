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

package com.dsh105.commodus.message;

/**
 * Offers a replacement for PowerMessage, a similar API built for Bukkit to handle special messages
 */
public final class Messages {

    // TODO: support for selectors, scores, translations?

    public static MessageBuilder builder() {
        return new CommodusMessageBuilder();
    }

    public static MessageBuilder builder(String content) {
        return new CommodusMessageBuilder().content(content);
    }

    public static Message fromJson(String json) {
        return builder().fromJson(json).build();
    }

    public static Message of(String content) {
        return builder(content).build();
    }
}