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

package com.dsh105.commodus;

import com.dsh105.commodus.bukkit.BukkitServerUtil;
import org.bukkit.Bukkit;

public enum ServerBrand {

    /**
     * Represents a Bukkit server
     */
    BUKKIT,

    /**
     * Represents a Spigot server
     */
    SPIGOT,

    /**
     * Represents a Sponge server
     */
    SPONGE,

    /**
     * Represents a Cauldron server
     */
    CAULDRON,

    /**
     * Represents an unknown server
     */
    UNKNOWN;

    private static ServerBrand SERVER_BRAND;

    static {
        SERVER_BRAND = detectBrand();
    }

    /**
     * Detects the brand of server that is currently running.
     *
     * @return Server brand currently active
     */
    public static ServerBrand detectBrand() {
        if (SERVER_BRAND == null) {
            try {
                Class.forName("org.bukkit.Bukkit");
                return initialiseBukkit(Bukkit.getVersion());
            } catch (ClassNotFoundException e) {
                // ignore, obviously not running Bukkit
            }

            // TODO: Sponge

            return UNKNOWN;
        }
        return SERVER_BRAND;
    }

    private static ServerBrand initialiseBukkit(String bukkitVersion) {
        if (bukkitVersion.contains("MCPC-Plus") || bukkitVersion.contains("Cauldron")) {
            return CAULDRON;
        } else if (bukkitVersion.contains("Spigot")) {
            return SPIGOT;
        }
        return BUKKIT;
    }

    /**
     * Gets the version of the currently running server.
     * <p/>
     * e.g. 1.8-R0.1, 1.7.10-R0.1
     *
     * @return Server version
     */
    public Version getPlatformVersion() {
        switch (getCapsule()) {
            case BUKKIT:
                return BukkitServerUtil.getPlatformVersion();
            case SPONGE:
                // TODO
            default:
                throw new IllegalStateException("Failed to detect version of unknown server brand.");
        }
    }

    /**
     * Gets the version of the underlying Minecraft server.
     * <p/>
     * e.g. 1.8, 1.7.10
     *
     * @return Minecraft server version
     */
    public Version getMinecraftVersion() {
        switch (getCapsule()) {
            case BUKKIT:
                return BukkitServerUtil.getMinecraftVersion();
            case SPONGE:
                // TODO
            default:
                throw new IllegalStateException("Failed to detect version of unknown server brand.");
        }
    }

    public Capsule getCapsule() {
        switch (this) {
            case BUKKIT:
            case SPIGOT:
            case CAULDRON:
                return Capsule.BUKKIT;
            case SPONGE:
                return Capsule.SPONGE;
            default:
                return Capsule.UNKNOWN;
        }
    }

    public enum Capsule {
        BUKKIT, SPONGE, UNKNOWN
    }
}