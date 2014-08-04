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

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Utilities for making the transition between names and UUIDs smoother and easier
 */
public class IdentUtil {

    private IdentUtil() {
    }

    /**
     * Returns whether the server version currently running primarily uses UUIDs instead of player names
     * <p>
     * UUIDs were introduced into the Bukkit API in 1.7.5
     *
     * @return true if the server supports the switch to UUIDs
     */
    public static boolean supportsUuid() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return ServerUtil.getVersion().isCompatible("1.7.5");
    }

    /**
     * Returns whether the ident of two offline players are identical
     *
     * @param player    first offline player
     * @param compareTo player to compare the first offline player to
     * @return true if the identification for both players is identical
     * @deprecated because DSH105 fails at English'ing, use {@link #areIdentical(org.bukkit.OfflinePlayer,
     * org.bukkit.OfflinePlayer)}
     */
    @Deprecated
    public static boolean isIdentical(OfflinePlayer player, OfflinePlayer compareTo) {
        return areIdentical(player, compareTo);
    }

    /**
     * Returns whether the ident of two offline players are identical
     *
     * @param player    first offline player
     * @param compareTo player to compare the first offline player to
     * @return true if the identification for both players is identical
     */
    public static boolean areIdentical(OfflinePlayer player, OfflinePlayer compareTo) {
        return getIdentificationForAsString(player).equals(getIdentificationForAsString(compareTo));
    }

    /**
     * Returns whether a given ident matches that of another offline player
     *
     * @param playerIdent the ident to compare against the player
     * @param compareTo   player to compare the first offline player to
     * @return true if the identification of the given player matches {@code playerIdent}
     */
    public static boolean areIdentical(String playerIdent, OfflinePlayer compareTo) {
        return playerIdent.equals(getIdentificationForAsString(compareTo));
    }

    /**
     * Returns the identification for a given player name
     * <p>
     * <strong>This call fetches UUIDs from Mojang servers</strong>
     *
     * @param playerName name of the player to retrieve a UUID for
     * @return identification for the given player name
     */
    public static Object getIdentificationFor(String playerName) {
        try {
            return UUIDFetcher.getUUIDOf(playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerName;
    }

    /**
     * Returns the identification for a given player
     *
     * @param player         player to fetch a UUID for
     * @param enableFetching if set to true, the UUID will be fetched from Mojang if {@code player} is offline
     * @return identification for the given player
     */
    public static Object getIdentificationFor(OfflinePlayer player, boolean enableFetching) {
        if (player instanceof Player) {
            if (supportsUuid()) {
                return player.getUniqueId();
            }
            return player.getName();
        } else {
            if (enableFetching) {
                return getIdentificationFor(player.getName());
            }
            return null;
        }
    }

    /**
     * Returns the identification for a given player
     * <p>
     * This call fetches results from Mojang servers if the provided player is offline
     *
     * @param player player to fetch a UUID for
     * @return identification for the given player
     */
    public static Object getIdentificationFor(OfflinePlayer player) {
        return getIdentificationFor(player, true);
    }

    /**
     * Returns the identification for a given player in string format
     * <p>
     * This call fetches results from Mojang servers if the provided player is offline
     *
     * @param player player to fetch a UUID for
     * @return identification for the given player in a
     */
    public static String getIdentificationForAsString(OfflinePlayer player) {
        return getIdentificationForAsString(player, true);
    }

    /**
     * Returns the identification for a given player in string format
     *
     * @param player         player to fetch a UUID for
     * @param enableFetching if set to true, the UUID will be fetched from Mojang if {@code player} is offline
     * @return identification for the given player
     */
    public static String getIdentificationForAsString(OfflinePlayer player, boolean enableFetching) {
        Object ident = getIdentificationFor(player, enableFetching);
        return ident == null ? null : ident.toString();
    }

    /**
     * Returns the player represented by the given identification (retrieved by using this class)
     * <p>
     * This method makes use of both player names and UUIDs to find the required online player
     *
     * @param identification identification to search with
     * @return player represented by the given identification, or null if the player is not online
     */
    public static Player getPlayerOf(Object identification) {
        if (supportsUuid()) {
            if (identification instanceof UUID) {
                return Bukkit.getPlayer((UUID) identification);
            } else if (identification instanceof String) {
                return Bukkit.getPlayer(StringUtil.convertUUID((String) identification));
            }
        } else if (identification instanceof String) {
            return Bukkit.getPlayerExact((String) identification);
        }
        return null;
    }
}