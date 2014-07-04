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

public class Log {

    private JavaPlugin plugin;
    private String prefix;
    private ConsoleCommandSender console;

    public Log(String prefix) {
        this.prefix = prefix;
    }

    public void setPlugin(JavaPlugin plugin) {
        if (this.plugin != null && plugin != null) {
            throw new IllegalArgumentException("Cannot set plugin twice!");
        }
        this.plugin = plugin;
    }

    public void log(String message) {
        info(message);
    }

    public void info(String message) {
        console(message);
    }

    public void warning(String message) {
        console(Level.WARNING, message);
    }

    public void severe(String message) {
        console(Level.SEVERE, message);
    }

    public void console(String message) {
        console(Level.INFO, message);
    }

    public void console(Level level, String message) {
        if (console == null) {
            console = Bukkit.getConsoleSender();
        }
        console.sendMessage("[" + prefix + "]" + level.getPrefix() + message);
    }
}