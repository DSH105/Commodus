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

import com.dsh105.commodus.GeneralUtil;
import com.dsh105.commodus.container.SpongePosition;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * General utilities associated with Sponge plugins.
 */
public class SpongeUtil {

    private static PluginContainer PLUGIN_CONTAINER;
    private static Game GAME;

    /**
     * Prepares Commodus for use inside a Sponge plugin.
     * <p/>
     * <strong>This method must be called before any functions provided by Commodus are called from within the Sponge
     * plugin.</strong>
     * <p/>
     * Note: The {@link org.spongepowered.api.Game} instance can be retrieved from inside the plugin instance via
     * dependency injection: {@code @com.google.inject.Inject Game game;}
     *
     * @param pluginInstance Plugin instance. Refers to the class annotated with the {@link
     *                       org.spongepowered.api.plugin.Plugin} annotation.
     * @param game           Game instance
     */
    public static void prepare(Object pluginInstance, Game game) {
        if (GAME != null || PLUGIN_CONTAINER != null) {
            throw new IllegalStateException("Sponge utilities cannot be prepared more than once!");
        }
        // throws IllegalStateException if it doesn't exist
        PLUGIN_CONTAINER = game.getPluginManager().fromInstance(pluginInstance).get();
        GAME = game;
    }

    /**
     * Gets the Sponge PluginContainer that is utilising the Commodus library.
     *
     * @return plugin container instance
     */
    public static PluginContainer getPluginContainer() {
        return PLUGIN_CONTAINER;
    }

    /**
     * Gets the Sponge Game instance, required for al core API access.
     *
     * @return The Sponge Game instance
     */
    public static Game getGame() {
        return GAME;
    }

    /**
     * Parses a location from a set of String arguments
     * <p/>
     * Arguments must be in the following order:
     * <ul>
     *     <li>World name</li>
     *     <li>X coordinate</li>
     *     <li>Y coordinate</li>
     *     <li>Z coordinate</li
     *     <li><b>Optional: </b>Yaw</li>
     *     <li><b>Optional: </b>Pitch</li>
     * </ul>
     *
     * @param startIndex Index to start parsing location from
     * @param args       String arguments to parse location from
     * @return A Location built from the given args, or null if it could not be parsed
     * @throws java.lang.IllegalStateException if the world at {@code args[startIndex]} does not exist
     */
    public static SpongePosition readLocation(int startIndex, String... args) {
        return GeneralUtil.readLocation(startIndex, args).toSponge();
    }
}