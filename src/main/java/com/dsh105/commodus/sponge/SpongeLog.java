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

package com.dsh105.commodus.sponge;

import com.dsh105.commodus.logging.Level;
import com.dsh105.commodus.logging.Log;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.source.ConsoleSource;

/**
 * Sponge implementation of the colour console logger
 */
public class SpongeLog extends Log {

    private ConsoleSource console;

    /**
     * Constructs a new Sponge-flavoured console logger with the given prefix
     *
     * @param prefix Prefix appended to all messages, excluding any opening/closing brackets e.g. [{@code prefix}]
     */
    public SpongeLog(String prefix) {
        super(prefix);
        console = getConsole();
    }

    protected static ConsoleSource getConsole() {
        // TODO: I think this is it, seeing as MixinMinecraftServer implements ConsoleSource...
        return (ConsoleSource) SpongeUtil.getGame().getServer().get();
    }

    @Override
    public void console(Level level, String message) {
        console.sendMessage(getLevelColor(level) + "[" + getPrefix() + "]" + level.getPrefix() + message);
    }

    private String getLevelColor(Level level) {
        switch (level) {
            case WARNING:
            case SEVERE:
                return TextColors.RED + " ";
            default:
                return "";
        }
    }
}