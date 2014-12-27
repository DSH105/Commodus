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

package com.dsh105.commodus.container;

import com.flowpowered.math.vector.Vector2f;
import org.spongepowered.api.world.Location;

public class SpongePosition {

    // TODO: docs

    private Location location;
    private Vector2f rotation;

    public SpongePosition(Location location, Vector2f rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    public Location getLocation() {
        return location;
    }

    public Vector2f getRotation() {
        return rotation;
    }
}