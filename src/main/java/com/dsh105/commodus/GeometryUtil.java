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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GeometryUtil {

    private GeometryUtil() {
    }

    public static float generateRandomFloat(float min, float max) {
        float f = min + (GeneralUtil.random().nextFloat() * ((1 + max) - min));
        return GeneralUtil.random().nextBoolean() ? f : -f;
    }

    public static float generateRandomFloat() {
        float f = GeneralUtil.random().nextFloat();
        return GeneralUtil.random().nextBoolean() ? f : -f;
    }

    public static List<Location> circle(Location origin, int radius, int height, boolean hollow, boolean sphere, boolean includeAir) {
        List<Location> blocks = new ArrayList<>();
        int cx = origin.getBlockX(),
                cy = origin.getBlockY(),
                cz = origin.getBlockZ();
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        Location l = new Location(origin.getWorld(), x, y, z);
                        if (!includeAir && l.getBlock().getType() == Material.AIR) {
                            continue;
                        }
                        blocks.add(l);
                    }
                }
            }
        }
        return blocks;
    }

    public static boolean isInBorder(Location center, Location toCheck, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = toCheck.getBlockX(), z1 = toCheck.getBlockZ();
        return !(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range));
    }

    public static <T extends Entity> List<T> getNearbyEntities(Class<T> entityType, Location origin, int range) {
        List<T> entities = new ArrayList<>();
        for (Entity entity : origin.getWorld().getEntities()) {
            if (range <= 0 || isInBorder(origin, entity.getLocation(), range)) {
                if (entityType.isAssignableFrom(entity.getClass())) {
                    entities.add((T) entity);
                }
            }
        }
        return entities;
    }

    public static List<Entity> getNearbyEntities(Location origin, int range) {
        return getNearbyEntities(Entity.class, origin, range);
    }

    public static List<Player> getNearbyPlayers(Location origin, int range) {
        return getNearbyEntities(Player.class, origin, range);
    }
}