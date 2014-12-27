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
import com.dsh105.commodus.GeometryUtil;
import com.dsh105.commodus.Transformer;
import com.dsh105.commodus.container.PositionContainer;
import com.dsh105.commodus.container.SpongePosition;
import com.flowpowered.math.vector.Vector2f;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;

public class SpongeGeometryUtil {

    /**
     * Generates a list of locations within a circular shape, dependent on the provided conditions
     *
     * @param origin     Origin or centre of the circle
     * @param radius     Radius of the circle
     * @param height     Height of the circle. If {@code height > 1}, the circle will become a cylinder
     * @param hollow     True if the generated circle is to be hollow
     * @param sphere     True if the shape generated is spherical
     * @param includeAir True if air can be included in the list of locations
     * @return A list of locations inside a generated circular shape
     */
    public static List<Location> circle(Location origin, int radius, int height, boolean hollow, boolean sphere, boolean includeAir) {
        return GeneralUtil.transform(PositionContainer.toSponge(GeometryUtil.circle(PositionContainer.from(origin), radius, height, hollow, sphere, includeAir)), new Transformer<SpongePosition, Location>() {
            @Override
            public Location transform(SpongePosition transmutable) {
                return transmutable.getLocation();
            }
        });
    }

    /**
     * Gets whether a certain location is within a given radius
     *
     * @param origin  Origin or centre
     * @param toCheck Location to check
     * @param range   Border range
     * @return True if the location is inside the border, false if not
     */
    public static boolean isInBorder(Location origin, Location toCheck, int range) {
        return GeometryUtil.isInBorder(PositionContainer.from(origin), PositionContainer.from(toCheck), range);
    }

    /**
     * Returns a list of entities near a given location
     *
     * @param typeRestriction Type (or super-type) of entity to include in the search
     * @param origin          Origin or centre of the area to search
     * @param range           Range to search within
     * @param <T>             Entity restriction to place on the search
     * @return A list of nearby entities
     */
    public static <T extends Entity> List<T> getNearbyEntities(Class<T> typeRestriction, Location origin, int range) {
        List<PositionContainer> containers = new ArrayList<>();
        for (Entity entity : origin.getExtent().getEntities()) {
            PositionContainer container = PositionContainer.from(entity.getLocation(), entity.getRotation());
            container.put(Entity.class, entity);
            containers.add(container);
        }
        List<PositionContainer> nearby = GeometryUtil.getNearby(PositionContainer.from(origin), range, containers);
        List<T> entities = new ArrayList<>();
        for (PositionContainer container : nearby) {
            Entity entity = container.get(Entity.class);
            container.clear();
            if (typeRestriction.isAssignableFrom(entity.getClass())) {
                entities.add((T) entity);
            }
        }
        return entities;
    }

    /**
     * Returns a list of entities near a given location
     *
     * @param origin Origin or centre of the area to search
     * @param range  Range to search within
     * @return A list of nearby entities
     */
    public static List<Entity> getNearbyEntities(Location origin, int range) {
        return getNearbyEntities(Entity.class, origin, range);
    }

    /**
     * Returns a list of players near a given location
     *
     * @param origin Origin or centre
     * @param range  Range to search within
     * @return A list of nearby players
     */
    public static List<Player> getNearbyPlayers(Location origin, int range) {
        return getNearbyEntities(Player.class, origin, range);
    }

    /**
     * Gets whether the block at the given position is filled with air
     *
     * @param positionContainer Position of the block to check
     * @return True if the block is air, false if not
     */
    public static boolean isAir(PositionContainer positionContainer) {
        return positionContainer.toSponge().getLocation().getBlock().getType() == BlockTypes.AIR;
    }
}