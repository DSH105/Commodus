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

import com.dsh105.commodus.StringUtil;
import com.dsh105.commodus.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Utilities for obtaining and comparing name and UUID identification for players
 */
public class BukkitIdentUtil {

    private BukkitIdentUtil() {
    }

    /**
     * Returns whether the server version currently running primarily uses UUIDs instead of player names
     * <p/>
     * UUIDs were introduced into the Bukkit API in 1.7.5
     *
     * @return True if the server supports the switch to UUIDs
     */
    public static boolean supportsUIDs() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return BukkitServerUtil.getMinecraftVersion().isCompatible("1.7.5");
    }

    /**
     * Returns the unique ID associated with a certain player name
     * <p/>
     * <strong>This call fetches UUIDs from Mojang servers</strong>
     *
     * @param playerName Name of the player to retrieve an ID for
     * @return UUID associated with the provided player name
     * @throws java.lang.IllegalArgumentException if an ID was not found for the given player name
     */
    public static UUID fetchUID(String playerName) {
        try {
            return UUIDFetcher.getUUIDOf(playerName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to retrieve ident for " + playerName, e);
        }
    }

    /**
     * Returns the identification for the given player appropriate to the currently running server version. The
     * identification is returned in string format for ease of use. {@link java.util.UUID#fromString(String)} may be
     * used to convert this identification to a UUID.
     * <p/>
     * If the active server version supports use of UUIDs (i.e. Bukkit API 1.7.5 or higher), a UUID will be returned in
     * String format. Otherwise, the player's name will be returned instead
     * <p/>
     * If fetching is enabled, the player's unique ID will be retrieved from Mojang's servers using {@link
     * com.dsh105.commodus.UUIDFetcher}.
     * <p/>
     * <strong>This method should NOT be run on the main thread if {@code fetch} is set to true.</strong>
     *
     * @param offlinePlayer Player to retrieve the unique ID for
     * @param fetch         If set to true and the provided player is offline, the ID will be fetching from Mojang's
     *                      servers
     * @return Identification for the given player in String format
     * @throws java.lang.IllegalArgumentException if fetching is enabled and retrieval of the given player's ID failed.
     */
    public static String getIdent(OfflinePlayer offlinePlayer, boolean fetch) {
        if (supportsUIDs()) {
            if (offlinePlayer.isOnline() && offlinePlayer instanceof Player) {
                return offlinePlayer.getUniqueId().toString();
            }

            if (fetch) {
                return fetchUID(offlinePlayer.getName()).toString();
            }
        }
        return offlinePlayer.getName();
    }

    /**
     * Returns the identification for the given player appropriate to the currently running server version. The
     * identification is returned in string format for ease of use. {@link java.util.UUID#fromString(String)} may be
     * used to convert this identification to a UUID.
     * <p/>
     * If the active server version supports use of UUIDs (i.e. Bukkit API 1.7.5 or higher), a UUID will be returned in
     * String format. Otherwise, the player's name will be returned instead.
     * <p/>
     * <strong>This will NOT fetch IDs from Mojang's servers.</strong>
     *
     * @param offlinePlayer Player to retrieve the unique ID for
     * @return Identification for the given player in String format, or null if not found
     */
    public static String getIdent(OfflinePlayer offlinePlayer) {
        return getIdent(offlinePlayer, false);
    }

    /**
     * Retrieves the {@link org.bukkit.entity.Player) that corresponds to the given unique ID.
     *
     * @param uniqueId Identification that represents the player to be retrieved
     * @return Player that is represented by the given ID, or null if not found
     */
    public static Player getPlayer(UUID uniqueId) {
        return supportsUIDs() ? Bukkit.getPlayer(uniqueId) : null;
    }

    /**
     * Retrieves the {@link org.bukkit.entity.Player} that is represented by the provided identification (either a
     * player name or UUID in string format).
     *
     * @param identification Identification that represents the player to be retrieved
     * @return Player that is represented by the given identification, or null if not found
     */
    public static Player getPlayer(String identification) {
        if (supportsUIDs()) {
            try {
                return getPlayer(StringUtil.convertUUID(identification));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return Bukkit.getPlayerExact(identification);
    }
}