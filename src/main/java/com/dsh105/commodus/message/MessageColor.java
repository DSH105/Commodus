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

import org.bukkit.ChatColor;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public enum MessageColor {

    BLACK('0', ChatColor.BLACK, TextColors.BLACK),
    DARK_BLUE('1', ChatColor.DARK_BLUE, TextColors.DARK_BLUE),
    DARK_GREEN('2', ChatColor.DARK_GREEN, TextColors.DARK_GREEN),
    DARK_AQUA('3', ChatColor.DARK_AQUA, TextColors.DARK_AQUA),
    DARK_RED('4', ChatColor.DARK_RED, TextColors.DARK_RED),
    DARK_PURPLE('5', ChatColor.DARK_PURPLE, TextColors.DARK_PURPLE),
    GOLD('6', ChatColor.GOLD, TextColors.GOLD),
    GRAY('7', ChatColor.GRAY, TextColors.GRAY),
    DARK_GRAY('8', ChatColor.DARK_GRAY, TextColors.DARK_GRAY),
    BLUE('9', ChatColor.BLUE, TextColors.BLUE),
    GREEN('a', ChatColor.GREEN, TextColors.GREEN),
    AQUA('b', ChatColor.AQUA, TextColors.AQUA),
    RED('c', ChatColor.RED, TextColors.RED),
    LIGHT_PURPLE('d', ChatColor.LIGHT_PURPLE, TextColors.LIGHT_PURPLE),
    YELLOW('e', ChatColor.YELLOW, TextColors.YELLOW),
    WHITE('f', ChatColor.WHITE, TextColors.WHITE);

    private char legacyChar;
    private ChatColor bukkitColor;
    private TextColor spongeColor;

    MessageColor(char legacyChar, ChatColor bukkitColor, TextColor spongeColor) {
        this.legacyChar = legacyChar;
        this.bukkitColor = bukkitColor;
        this.spongeColor = spongeColor;
    }

    public char getLegacyChar() {
        return legacyChar;
    }

    public ChatColor getBukkitColor() {
        return bukkitColor;
    }

    public TextColor getSpongeColor() {
        return spongeColor;
    }
}