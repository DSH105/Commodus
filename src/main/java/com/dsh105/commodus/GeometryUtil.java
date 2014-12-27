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

import com.dsh105.commodus.bukkit.BukkitGeometryUtil;
import com.dsh105.commodus.container.PositionContainer;
import com.dsh105.commodus.sponge.SpongeGeometryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Collection of maths-based geometry calculating methods
 */
public class GeometryUtil {

    private GeometryUtil() {
    }

    /**
     * Generates a random floating point value between the given minimum and maximum
     *
     * @param min Minimum value, inclusive
     * @param max Maximum value, inclusive
     * @return A random floating point value
     */
    public static float generateRandomFloat(float min, float max) {
        float f = min + (GeneralUtil.random().nextFloat() * ((1 + max) - min));
        return GeneralUtil.random().nextBoolean() ? f : -f;
    }

    /**
     * Generates a random floating point value
     *
     * @return A random floating point value
     */
    public static float generateRandomFloat() {
        float f = GeneralUtil.random().nextFloat();
        return GeneralUtil.random().nextBoolean() ? f : -f;
    }

    /**
     * Generates a list of locations within a circular shape, dependent on the provided conditions
     * <p/>
     * <p/>
     * See {@link com.dsh105.commodus.bukkit.BukkitGeometryUtil#circle(org.bukkit.Location, int, int, boolean, boolean,
     * boolean)} and {@link com.dsh105.commodus.sponge.SpongeGeometryUtil#circle(org.spongepowered.api.world.Location,
     * int, int, boolean, boolean, boolean)} for use of this in Bukkit and Sponge plugins.
     *
     * @param origin     Origin or centre of the circle
     * @param radius     Radius of the circle
     * @param height     Height of the circle. If {@code height > 1}, the circle will become a cylinder
     * @param hollow     True if the generated circle is to be hollow
     * @param sphere     True if the shape generated is spherical
     * @param includeAir True if air can be included in the list of locations
     * @return A list of locations inside a generated circular shape
     */
    public static List<PositionContainer> circle(PositionContainer origin, int radius, int height, boolean hollow, boolean sphere, boolean includeAir) {
        List<PositionContainer> blocks = new ArrayList<>();
        int cx = (int) origin.getX(),
                cy = (int) origin.getY(),
                cz = (int) origin.getZ();
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        PositionContainer position = new PositionContainer(origin.getWorldUID(), x, y, z);
                        if (!includeAir && isAir(position)) {
                            continue;
                        }
                        blocks.add(position);
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * Gets whether a certain location is within a given radius.
     * <p/>
     * See {@link com.dsh105.commodus.bukkit.BukkitGeometryUtil#isInBorder(org.bukkit.Location, org.bukkit.Location,
     * int)} and {@link com.dsh105.commodus.sponge.SpongeGeometryUtil#isInBorder(org.spongepowered.api.world.Location,
     * org.spongepowered.api.world.Location, int)} for use of this in Bukkit and Sponge plugins.
     *
     * @param origin  Origin or centre
     * @param toCheck Location to check
     * @param range   Border range
     * @return True if the location is inside the border, false if not
     */
    public static boolean isInBorder(PositionContainer origin, PositionContainer toCheck, int range) {
        int x = (int) origin.getX(), z = (int) origin.getZ();
        int x1 = (int) toCheck.getX(), z1 = (int) toCheck.getZ();
        return !(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range));
    }

    /**
     * Returns a list of entities near a given location.
     * <p/>
     * See {@link com.dsh105.commodus.bukkit.BukkitGeometryUtil#getNearbyEntities(org.bukkit.Location, int)} and {@link
     * com.dsh105.commodus.sponge.SpongeGeometryUtil#getNearbyEntities(org.spongepowered.api.world.Location, int)} for
     * use of this in Bukkit and Sponge plugins.
     *
     * @param origin     Origin or centre of the area to search
     * @param range      Range to search within
     * @param candidates List of possible candidates to search through
     * @return A list of nearby entities
     */
    public static List<PositionContainer> getNearby(PositionContainer origin, int range, Collection<PositionContainer> candidates) {
        List<PositionContainer> nearby = new ArrayList<>();
        for (PositionContainer candidate : candidates) {
            if (range <= 0 || isInBorder(origin, candidate, range)) {
                nearby.add(candidate);
            }
        }
        return nearby;
    }

    /**
     * Gets whether the block at the given position is filled with air
     * <p/>
     * Polls both {@link com.dsh105.commodus.bukkit.BukkitGeometryUtil#isAir(com.dsh105.commodus.container.PositionContainer)}
     * and {@link } before returning a value.
     *
     * @param positionContainer Position of the block to check
     * @return True if the given position contains a block of air, false if otherwise
     * @throws java.lang.IllegalStateException if neither the Bukkit nor Sponge API could be accessed
     */
    public static boolean isAir(PositionContainer positionContainer) {
        try {
            return BukkitGeometryUtil.isAir(positionContainer);
        } catch (NoClassDefFoundError ignored) {
        }
        try {
            return SpongeGeometryUtil.isAir(positionContainer);
        } catch (NoClassDefFoundError e) {
            // neither could be found
            throw new IllegalStateException("Failed to access the appropriate utilities for the running server API.", e);
        }
    }
}