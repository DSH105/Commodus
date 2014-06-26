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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtil {

    public static int MC_VERSION_NUMERIC = Integer.valueOf(getServerVersion().replaceAll("[^0-9]", ""));
    public static int BUKKIT_VERSION_NUMERIC = Integer.valueOf(getBukkitVersion().replaceAll("[^0-9]", ""));

    public static String getServerVersion() {
        return MinecraftReflection.getVersionTag();
    }

    // Thanks ProtocolLib <3
    public static String getBukkitVersion() {
        Pattern versionPattern = Pattern.compile(".*\\(.*MC.\\s*([a-zA-z0-9\\-\\.]+)\\s*\\)");
        Matcher version = versionPattern.matcher(Bukkit.getServer().getVersion());

        if (version.matches() && version.group(1) != null) {
            return version.group(1);
        } else {
            return "";
        }
    }
}