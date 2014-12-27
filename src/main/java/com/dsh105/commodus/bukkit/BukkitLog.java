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

package com.dsh105.commodus.bukkit;

import com.dsh105.commodus.logging.Level;
import com.dsh105.commodus.logging.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Bukkit implementation of the colour console logger
 */
public class BukkitLog extends Log {

    private ConsoleCommandSender console;

    /**
     * Constructs a new Bukkit-flavoured console logger with the given prefix
     *
     * @param prefix Prefix appended to all messages, excluding any opening/closing brackets e.g. [{@code prefix}]
     */
    public BukkitLog(String prefix) {
        super(prefix);
        console = Bukkit.getConsoleSender();
    }

    @Override
    public void console(Level level, String message) {
        console.sendMessage(getLevelColor(level) + "[" + getPrefix() + "]" + level.getPrefix() + message);
    }

    private String getLevelColor(Level level) {
        switch (level) {
            case WARNING:
            case SEVERE:
                return ChatColor.RED + " ";
            default:
                return "";
        }
    }
}