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

import com.captainbern.minecraft.reflection.MinecraftReflection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IdentUtil {

    public static boolean supportsUuid() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return GeneralUtil.toInteger(MinecraftReflection.getVersionTag()) >= 172;
    }

    public static Object getIdentificationFor(String playerName) {
        try {
            return UUIDFetcher.getUUIDOf(playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerName;
    }

    public static Object getIdentificationFor(OfflinePlayer player) {
        if (player instanceof Player) {
            if (supportsUuid()) {
                return player.getUniqueId();
            }
            return player.getName();
        } else {
            return getIdentificationFor(player.getName());
        }
    }

    public static String getIdentificationForAsString(OfflinePlayer player) {
        return getIdentificationFor(player).toString();
    }

    public static Player getPlayerOf(Object identification) {
        if (supportsUuid()) {
            if (identification instanceof UUID) {
                return Bukkit.getPlayer((UUID) identification);
            } else if (identification instanceof String) {
                return Bukkit.getPlayer(UUID.fromString((String) identification));
            }
        } else if (identification instanceof String) {
            return Bukkit.getPlayerExact((String) identification);
        }
        return null;
    }
}