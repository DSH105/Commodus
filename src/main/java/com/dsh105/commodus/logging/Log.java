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

package com.dsh105.commodus.logging;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Basic logging to a server console
 * <p>
 * Supports chat colours :)
 */
public class Log {

    private JavaPlugin plugin;
    private String prefix;
    private ConsoleCommandSender console;

    /**
     * Constructs a new console logger with the given prefix
     *
     * @param prefix prefix appended to all messages, excluding any opening/closing brackets e.g. [{@code prefix}] Hello
     *               world.
     */
    public Log(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Logs a coloured message to the console
     *
     * @param message message to log
     */
    public void log(String message) {
        info(message);
    }

    /**
     * Logs a coloured message to the console
     *
     * @param message message to log
     */
    public void info(String message) {
        console(message);
    }

    /**
     * Logs a coloured message to the console, with the {@link Level#WARNING} colour appended
     *
     * @param message message to log
     */
    public void warning(String message) {
        console(Level.WARNING, message);
    }

    /**
     * Logs a coloured message to the console, with the {@link Level#SEVERE} colour appended
     *
     * @param message message to log
     */
    public void severe(String message) {
        console(Level.SEVERE, message);
    }

    /**
     * Logs a coloured message to the console
     *
     * @param message message to log
     */
    public void console(String message) {
        console(Level.INFO, message);
    }

    /**
     * Logs a coloured message to the console with the given level
     *
     * @param message message to log
     */
    public void console(Level level, String message) {
        if (console == null) {
            console = Bukkit.getConsoleSender();
        }
        console.sendMessage("[" + prefix + "]" + level.getPrefix() + message);
    }
}