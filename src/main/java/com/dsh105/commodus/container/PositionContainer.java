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

import com.dsh105.commodus.sponge.SpongeUtil;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Acts as a 'bridge' for locations between different server platforms, storing the necessary coordinate information
 * require for conversion.
 * <p/>
 * All coordinate values are specific to their respective platforms, and it is suggested to consult the appropriate
 * documentation to confirm their usage.
 */
public class PositionContainer extends Container<PositionContainer> implements Cloneable {

    private UUID worldId;
    private double x;
    private double y;
    private double z;
    private float xRotation; // Bukkit -> yaw
    private float yRotation; // Bukkit -> pitch

    /**
     * Constructs a new container from the given coordinates and World ID
     *
     * @param worldId The ID of the world in which the new location is positioned
     * @param x       X-coordinate of the new container
     * @param y       Y-coordinate of the new container
     * @param z       Z-coordinate of the new container
     */
    public PositionContainer(UUID worldId, double x, double y, double z) {
        this(worldId, x, y, z, 0.0F, 0.0F);
    }

    public PositionContainer(UUID worldId, double x, double y, double z, float xRotation, float yRotation) {
        this.worldId = worldId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
    }

    /**
     * Constructs a container for the given {@link org.bukkit.Location}.
     *
     * @param bukkitLocation Location object to construct a container for.
     * @return A new container representing the given location.
     */
    public static PositionContainer from(org.bukkit.Location bukkitLocation) {
        return new PositionContainer(bukkitLocation.getWorld().getUID(), bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ());
    }


    /**
     * Constructs a container for the given {@link org.spongepowered.api.world.Location}.
     *
     * @param spongeLocation Location object to construct a container for.
     * @return A new container representing the given location.
     */
    public static PositionContainer from(org.spongepowered.api.world.Location spongeLocation) {
        return from(spongeLocation, new Vector2f());
    }

    /**
     * Constructs a container for the given {@link org.spongepowered.api.world.Location} and {@link com.flowpowered.math.vector.Vector2f} rotation.
     *
     * @param spongeLocation Location object to construct a container for.
     * @param rotation       Rotation of the position
     * @return A new container representing the given location and rotation.
     */
    public static PositionContainer from(org.spongepowered.api.world.Location spongeLocation, Vector2f rotation) {
        // TODO: surely there's a way to get the UUID for a location's world...
        return new PositionContainer(/*spongeLocation.getExtent().getUniqueID()*/null, spongeLocation.getPosition().getX(), spongeLocation.getPosition().getY(), spongeLocation.getPosition().getZ(), rotation.getX(), rotation.getY());
    }

    /**
     * Constructs a container for the given {@link com.dsh105.commodus.container.SpongePosition}.
     *
     * @param spongePosition Position to construct the container for
     * @return A new container representing the given position.
     */
    public static PositionContainer from(SpongePosition spongePosition) {
        Vector3d position = spongePosition.getLocation().getPosition();
        Vector2f rotation = spongePosition.getRotation();
        // TODO: surely there's a way to get the UUID for a location's world...
        return new PositionContainer(/*spongeLocation.getExtent().getUniqueID()*/null, position.getX(), position.getY(), position.getZ(), rotation.getX(), rotation.getY());
    }

    /**
     * Converts a collection of containers to new {@link org.bukkit.Location} objects
     *
     * @param positionContainers Containers to convert
     * @return A list of new Location objects with the respective coordinates provided in {@code locationContainers}
     */
    public static List<org.bukkit.Location> toBukkit(Collection<PositionContainer> positionContainers) {
        List<org.bukkit.Location> locations = new ArrayList<>();
        for (PositionContainer container : positionContainers) {
            locations.add(container.toBukkit());
        }
        return locations;
    }

    /**
     * Converts a collection of containers to new {@link com.dsh105.commodus.container.SpongePosition} objects
     *
     * @param positionContainers Containers to convert
     * @return A list of new SpongePosition objects with the respective coordinates provided in {@code
     * locationContainers}
     */
    public static List<SpongePosition> toSponge(Collection<PositionContainer> positionContainers) {
        List<SpongePosition> locations = new ArrayList<>();
        for (PositionContainer container : positionContainers) {
            locations.add(container.toSponge());
        }
        return locations;
    }

    /**
     * Converts this container to a new {@link org.bukkit.Location} object.
     *
     * @return A new Location with the coordinates and rotation stored in this container.
     */
    public org.bukkit.Location toBukkit() {
        return new org.bukkit.Location(Bukkit.getWorld(worldId), x, y, z);
    }

    /**
     * Converts this container to a new {@link com.dsh105.commodus.container.SpongePosition} object.
     *
     * @return A new SpongePosition with the coordinates and rotation stored in this container.
     */
    public SpongePosition toSponge() {
        return new SpongePosition(new org.spongepowered.api.world.Location(SpongeUtil.getGame().getServer().get().getWorld(worldId).get(), new Vector3d(x, y, z)), new Vector2f(xRotation, yRotation));
    }

    /**
     * Constructs a new {@link com.dsh105.commodus.container.Vector3dContainer} based on this position.
     *
     * @return The new vector with the coordinates of this location as its components
     */
    public Vector3dContainer toVector() {
        return new Vector3dContainer(x, y, z);
    }

    /**
     * Gets the unique ID for this world.
     *
     * @return The ID of the world that this location belongs in.
     */
    public UUID getWorldUID() {
        return worldId;
    }

    /**
     * Gets the x-coordinate of this location.
     *
     * @return X-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this location.
     *
     * @return Y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the z-coordinate of this location.
     *
     * @return Z-coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Gets the rotation of this location on the x-plane.
     * <p/>
     * Referred to as yaw in the Bukkit API.
     *
     * @return Rotation of this location on the x-plane
     */
    public float getXRotation() {
        return xRotation;
    }

    /**
     * Gets the rotation of this location on the y-plane.
     * <p/>
     * Referred to as pitch in the Bukkit API.
     *
     * @return Rotation of this location on the y-plane
     */
    public float getYRotation() {
        return yRotation;
    }

    /**
     * Sets the x-coordinate of this location
     *
     * @param x The new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this location
     *
     * @param y The new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the z-coordinate of this location
     *
     * @param z The new z-coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Sets the rotation of this location on the x-plane.
     * <p/>
     * Referred to as yaw in the Bukkit API.
     *
     * @param xRotation The new rotation on the x-plane
     */
    public void setXRotation(float xRotation) {
        this.xRotation = xRotation;
    }

    /**
     * Gets the rotation of this location on the y-plane.
     * <p/>
     * Referred to as pitch in the Bukkit API.
     *
     * @param yRotation The new rotation on the y-plane
     */
    public void setYRotation(float yRotation) {
        this.yRotation = yRotation;
    }

    @Override
    public PositionContainer clone() {
        try {
            return (PositionContainer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}