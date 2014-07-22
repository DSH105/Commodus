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
import com.dsh105.commodus.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtil {

    private ServerUtil() {
    }

    @Deprecated
    private static String MC_PACKAGE_NAME;

    private static Version SERVER_VERSION;
    private static Version BUKKIT_VERSION;

    public static boolean isCauldron() {
        // "meh"
        return Bukkit.getServer().getVersion().contains("Cauldron") || Bukkit.getServer().getVersion().contains("MCPC-Plus");
    }

    public static Version getVersion() {
        if (SERVER_VERSION == null) {
            SERVER_VERSION = new Version(getServerVersion());
        }
        return SERVER_VERSION;
    }

    // Thanks ProtocolLib <3
    public static Version getBukkitVersion() {
        if (BUKKIT_VERSION == null) {
            Pattern versionPattern = Pattern.compile(".*\\(.*MC.\\s*([a-zA-z0-9\\-\\.]+)\\s*\\)");
            Matcher version = versionPattern.matcher(Bukkit.getServer().getVersion());

            if (version.matches() && version.group(1) != null) {
                BUKKIT_VERSION = new Version(version.group(1));
            } else {
                return null;
            }
        }
        return BUKKIT_VERSION;
    }

    public static int[] getNumericServerVersion() {
        return getVersion().getNumericVersion();
    }

    public static int[] getNumericServerVersion(String serverVersion) {
        return new Version(serverVersion).getNumericVersion();
    }

    public static String getServerVersion() {
        try {
            return MinecraftReflection.getVersionTag();
        } catch (NoClassDefFoundError e) {
            return getMCPackage();
        }
    }

    @Deprecated
    public static String getMCPackage() {
        if (MC_PACKAGE_NAME == null) {
            MC_PACKAGE_NAME = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if (MC_PACKAGE_NAME == null || MC_PACKAGE_NAME.isEmpty()) {
                // Cauldron/MCPC+ hack
                MC_PACKAGE_NAME = (String) Reflection.invokeStatic(Reflection.getMethod(Reflection.getClass("org.bukkit.plugin.java.PluginClassLoader"), "getNativeVersion"));
            }
        }
        return MC_PACKAGE_NAME;
    }

    public static Player getOnlinePlayer(int index) {
        List<Player> onlinePlayers = getOnlinePlayers();
        if (index >= onlinePlayers.size()) {
            return null;
        }
        return onlinePlayers.get(index);
    }

    public static List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        try {
            Method onlinePlayersMethod = Bukkit.class.getMethod("getOnlinePlayers");
            if (onlinePlayersMethod.getReturnType().equals(Collection.class)) {
                Collection<Player> playerCollection = (Collection<Player>) onlinePlayersMethod.invoke(null, new Object[0]);
                if (playerCollection instanceof List) {
                    onlinePlayers = (List<Player>) playerCollection;
                } else {
                    onlinePlayers = new ArrayList<>(playerCollection);
                }
            } else {
                onlinePlayers = Arrays.asList((Player[]) onlinePlayersMethod.invoke(null, new Object[0]));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return onlinePlayers;
    }
}