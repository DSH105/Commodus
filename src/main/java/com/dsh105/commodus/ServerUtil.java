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

import com.captainbern.minecraft.reflection.MinecraftMethods;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.dsh105.commodus.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for gaining and manipulating server information
 */
public class ServerUtil {

    @Deprecated
    private static String MC_PACKAGE_NAME;
    private static Version SERVER_VERSION;
    private static Version BUKKIT_VERSION;
    private ServerUtil() {
    }

    /**
     * Gets whether the server running is a Cauldron (MCPC+) server
     * <p/>
     * Cauldrons and Bukkits hold different things; Bukkits usually hold cats
     *
     * @return true if this server is a Cauldron instead of a Bukkit
     */
    public static boolean isCauldron() {
        // "meh"
        return Bukkit.getServer().getVersion().contains("Cauldron") || Bukkit.getServer().getVersion().contains("MCPC-Plus");
    }

    /**
     * Returns a Version to represent the currently running server version
     *
     * @return Version object representative of this server's installed version
     */
    public static Version getVersion() {
        if (SERVER_VERSION == null) {
            SERVER_VERSION = new Version(getServerVersion());
        }
        return SERVER_VERSION;
    }

    /**
     * Returns the server's Bukkit version as a Version object
     *
     * @return a Version object representing the server's Bukkit version
     */
    public static Version getBukkitVersion() {
        // Thanks ProtocolLib <3

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

    /**
     * Returns the server's Bukkit version as a numeric array
     *
     * @return numeric array representing the server version currently running
     */
    public static int[] getNumericServerVersion() {
        return getVersion().getNumericVersion();
    }

    /**
     * Returns the given version as a numeric array
     *
     * @param serverVersion version to convert to a numeric array
     * @return numeric array representing the given  version currently running
     */
    public static int[] getNumericServerVersion(String serverVersion) {
        return new Version(serverVersion).getNumericVersion();
    }

    /**
     * Returns the package tag of the currently running server version, commonly used in accessing NMS/OBC classes
     *
     * @return the package tag of the currently running server version
     */
    public static String getVersionTag() {
        return getServerVersion();
    }

    /**
     * Returns the package tag of the currently running server version, commonly used in accessing NMS/OBC classes
     *
     * @return the package tag of the currently running server version
     * @deprecated use {@link #getVersionTag()}
     */
    @Deprecated
    public static String getServerVersion() {
        try {
            return MinecraftReflection.getVersionTag();
        } catch (NoClassDefFoundError e) {
            return getMCPackage();
        }
    }

    /**
     * Returns the package tag of the currently running server version, commonly used in accessing NMS/OBC classes
     *
     * @return the package tag of the currently running server version
     * @deprecated use {@link #getVersionTag()} for a safer, more accurate implementation
     */
    @Deprecated
    public static String getMCPackage() {
        if (MC_PACKAGE_NAME == null) {
            MC_PACKAGE_NAME = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            if (MC_PACKAGE_NAME == null || MC_PACKAGE_NAME.isEmpty()) {
                // Cauldron/MCPC+ hack
                MC_PACKAGE_NAME = (String) Reflection.invokeStatic(Reflection.getMethod(Reflection.getClass("org.bukkit.plugin.java.PluginClassLoader"), "getNativeVersion"));
            }
        }
        return MC_PACKAGE_NAME;
    }

    /**
     * Returns the player online at the given index in the {@link #getOnlinePlayers()} list
     *
     * @param index index to retrieve
     * @return online player at the given index
     */
    public static Player getOnlinePlayer(int index) {
        List<Player> onlinePlayers = getOnlinePlayers();
        if (index >= onlinePlayers.size()) {
            return null;
        }
        return onlinePlayers.get(index);
    }

    /**
     * Gets a list of players currently online
     * <p/>
     * This implementation includes a workaround for {@link org.bukkit.Bukkit#getOnlinePlayers()} returning an array in
     * older releases of CraftBukkit, instead of a Collection in more recent releases. Essentially, this adds backwards
     * compatibility with older versions of CraftBukkit without having to adjust much in your plugin.
     * <p/>
     * It's ugly, but it works and provides backwards compatibility
     *
     * @return a list of all online players
     */
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

    /**
     * Sends the given packet to a certain player
     *
     * @param player player to send the packet to
     * @param packet packet to send
     */
    public static void sendPacket(Object packet, Player player) {
        try {
            MinecraftMethods.sendPacket(player, packet);
            return;
        } catch (NoClassDefFoundError e) {
            // do nothing, continue with the backup plan
        }

        Class<?> packetClass = Reflection.getNMSClass("Packet");
        if (!packetClass.isAssignableFrom(packet.getClass())) {
            throw new IllegalArgumentException("Object to send must be a subclass of " + packetClass.getCanonicalName());
        }

        Method getHandle = Reflection.getMethod(player.getClass(), "getHandle");
        Field connection = Reflection.getField(Reflection.getNMSClass("EntityPlayer"), "playerConnection");
        Method sendPacket = Reflection.getMethod(connection.getType(), "sendPacket", packetClass);

        Object playerHandle = Reflection.invoke(getHandle, player);
        Object playerConnection = Reflection.getFieldValue(connection, playerHandle);
        Reflection.invoke(sendPacket, playerConnection, packet);
    }

    /**
     * Sends the given packet to certain set of players
     *
     * @param players players to send the packet to
     * @param packet  packet to send
     */
    public static void sendPacket(Object packet, Collection<Player> players) {
        for (Player player : players) {
            sendPacket(packet, player);
        }
    }

    /**
     * Sends the given packet to certain set of players
     *
     * @param players players to send the packet to
     * @param packet  packet to send
     */
    public static void sendPacket(Object packet, Player... players) {
        if (players != null) {
            sendPacket(packet, Arrays.asList(players));
        }
    }
}