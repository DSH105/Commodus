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

import com.flowpowered.math.vector.Vector3f;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Acts as a 'bridge' for the basic Vector (3d) found in Bukkit between different server platforms, storing the
 * necessary components information require for conversion.
 */
public class Vector3dContainer extends Container<Vector3dContainer> {

    private double x;
    private double y;
    private double z;

    public Vector3dContainer(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a container for the given {@link org.bukkit.util.Vector}.
     *
     * @param bukkitVector Vector object to construct a container for.
     * @return A new container representing the given Vector.
     */
    public static Vector3dContainer from(org.bukkit.util.Vector bukkitVector) {
        return new Vector3dContainer(bukkitVector.getX(), bukkitVector.getY(), bukkitVector.getZ());
    }

    /**
     * Constructs a container for the given {@link com.flowpowered.math.vector.Vector3f}.
     *
     * @param spongeVector Vector object to construct a container for.
     * @return A new container representing the given Vector.
     */
    public static Vector3dContainer from(com.flowpowered.math.vector.Vector3f spongeVector) {
        return new Vector3dContainer(spongeVector.getX(), spongeVector.getY(), spongeVector.getZ());
    }

    /**
     * Converts a collection of vectors to new {@link org.bukkit.util.Vector} objects
     *
     * @param vectors Vectors to convert
     * @return A list of new Vector objects with the respective components provided in {@code vectors}
     */
    public static List<Vector> toBukkit(Collection<Vector3dContainer> vectors) {
        List<org.bukkit.util.Vector> locations = new ArrayList<>();
        for (Vector3dContainer container : vectors) {
            locations.add(container.toBukkit());
        }
        return locations;
    }

    /**
     * Converts a collection of containers to new {@link org.bukkit.util.Vector} objects
     *
     * @param vectors Containers to convert
     * @return A list of new Location objects with the respective coordinates provided in {@code vectors}
     */
    public static List<com.flowpowered.math.vector.Vector3f> toSponge(Collection<Vector3dContainer> vectors) {
        List<com.flowpowered.math.vector.Vector3f> locations = new ArrayList<>();
        for (Vector3dContainer container : vectors) {
            locations.add(container.toSponge());
        }
        return locations;
    }

    /**
     * Converts this container to a new {@link org.bukkit.util.Vector} object.
     *
     * @return A new Vector with the components stored in this container.
     */
    public org.bukkit.util.Vector toBukkit() {
        return new Vector(x, y, z);
    }

    /**
     * Converts this container to a new {@link com.flowpowered.math.vector.Vector3f} object.
     *
     * @return A new Vector with the components stored in this container.
     */
    public com.flowpowered.math.vector.Vector3f toSponge() {
        return new Vector3f(x, y, z);
    }

    /**
     * Gets the x component
     *
     * @return X component
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y component
     *
     * @return Y component
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the z component
     *
     * @return Z component
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the x component
     *
     * @param x The new x component
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y component
     *
     * @param y The new y component
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the z component
     *
     * @param z The new z component
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the magnitude of this vector squared
     *
     * @return Magnitude of this vector squared
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Gets the magnitude of this vector
     *
     * @return Magnitude of this vector
     */
    public double length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Converts this vector to a unit vector
     *
     * @return This vector
     */
    public Vector3dContainer normalize() {
        return divide(length());
    }

    /**
     * Adds a vector to this one.
     *
     * @param vector3d The vector to subtract
     * @return This vector
     */
    public Vector3dContainer add(Vector3dContainer vector3d) {
        return add(vector3d.x, vector3d.y, vector3d.z);
    }

    /**
     * Adds a factor to this vector.
     * @param factor The factor
     * @return This vector
     */
    public Vector3dContainer add(double factor) {
        return add(factor, factor, factor);
    }

    /**
     * Adds a given set of components to this vector.
     * @param x The factor to add to this x component
     * @param y The factor to add to this y component
     * @param z The factor to add to this z component
     * @return This vector
     */
    public Vector3dContainer add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Subtracts a vector from this one.
     *
     * @param vector3d The vector to subtract
     * @return This vector
     */
    public Vector3dContainer subtract(Vector3dContainer vector3d) {
        return subtract(vector3d.x, vector3d.y, vector3d.z);
    }

    /**
     * Subtracts a factor from this vector.
     * @param factor The factor
     * @return This vector
     */
    public Vector3dContainer subtract(double factor) {
        return subtract(factor, factor, factor);
    }

    /**
     * Subtracts a given set of components from this vector.
     * @param x The factor to subtract from this x component
     * @param y The factor to subtract from this y component
     * @param z The factor to subtract from this z component
     * @return This vector
     */
    public Vector3dContainer subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Multiplies this vector by another.
     *
     * @param vector3d The vector to multiply by
     * @return This vector
     */
    public Vector3dContainer multiply(Vector3dContainer vector3d) {
        return multiply(vector3d.x, vector3d.y, vector3d.z);
    }

    /**
     * Multiplies this vector by a factor
     * @param factor The factor
     * @return This vector
     */
    public Vector3dContainer multiply(double factor) {
        return multiply(factor, factor, factor);
    }

    /**
     * Multiplies this vector by a given set of components
     * @param x The factor to multiply this x component by
     * @param y The factor to multiply this y component by
     * @param z The factor to multiply this z component by
     * @return This vector
     */
    public Vector3dContainer multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * Divides this vector by another.
     *
     * @param vector3d The vector to divide by
     * @return This vector
     */
    public Vector3dContainer divide(Vector3dContainer vector3d) {
        return divide(vector3d.x, vector3d.y, vector3d.z);
    }

    /**
     * Divides this vector by a factor
     * @param factor The factor
     * @return This vector
     */
    public Vector3dContainer divide(double factor) {
        return divide(factor, factor, factor);
    }

    /**
     * Divides this vector by a given set of components
     * @param x The factor to divide this x component by
     * @param y The factor to divide this y component by
     * @param z The factor to divide this z component by
     * @return This vector
     */
    public Vector3dContainer divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
}