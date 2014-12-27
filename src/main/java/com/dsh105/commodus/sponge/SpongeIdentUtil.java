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

import com.dsh105.commodus.UUIDFetcher;
import org.spongepowered.api.entity.player.Player;

import java.util.UUID;

/**
 * Utilities for obtaining and comparing name and UUID identification for players
 */
public class SpongeIdentUtil {

    private SpongeIdentUtil() {
    }

    /**
     * Returns the unique ID associated with a certain player name
     * <p/>
     * <strong>This call fetches UUIDs from Mojang servers</strong>
     *
     * @param playerName Name of the player to retrieve an ID for
     * @return UUID associated with the provided player name
     * @throws IllegalArgumentException if an ID was not found for the given player name
     */
    public static UUID fetchUID(String playerName) {
        try {
            return UUIDFetcher.getUUIDOf(playerName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to retrieve ident for " + playerName, e);
        }
    }

    /**
     * Returns the identification for the given player appropriate to the currently running server version. In the case
     * of Sponge, a UUID will always be returned, whereas the Bukkit alternative may often return the player name
     * instead (see {@link SpongeIdentUtil}).
     * <p/>
     * If fetching is enabled, the player's unique ID will be retrieved from Mojang's servers using {@link
     * com.dsh105.commodus.UUIDFetcher}.
     * <p/>
     * <strong>This method should NOT be run on the main thread if {@code fetch} is set to true.</strong>
     *
     * @param player Player to retrieve the unique ID for
     * @param fetch  If set to true and the provided player is offline, the ID will be fetching from Mojang's servers
     * @return Identification for the given player in String format, or null if not found
     * @throws IllegalArgumentException if fetching is enabled and retrieval of the given player's ID failed.
     */
    public static UUID getIdent(Player player, boolean fetch) {
        if (player.isOnline()) {
            return player.getUniqueId();
        }

        if (fetch) {
            return fetchUID(player.getName());
        }
        return null;
    }

    /**
     * Returns the identification for the given player appropriate to the currently running server version. In the case
     * of Sponge, a UUID will always be returned, whereas the Bukkit alternative may often return the player name
     * instead (see {@link SpongeIdentUtil}).
     * <p/>
     * <strong>This will NOT fetch IDs from Mojang's servers.</strong>
     *
     * @param player Player to retrieve the unique ID for
     * @return Identification for the given player in String format, or null if not found
     */
    public static UUID getIdent(Player player) {
        return getIdent(player, false);
    }

    /**
     * Retrieves the {@link org.bukkit.entity.Player) that corresponds to the given unique ID.
     *
     * @param uniqueId Identification that represents the player to be retrieved
     * @return Player that is represented by the given ID, or null if not found
     */
    public static Player getPlayer(UUID uniqueId) {
        return SpongeUtil.getGame().getServer().get().getPlayer(uniqueId).orNull();
    }
}