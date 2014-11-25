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

package com.dsh105.commodus.particle;

import com.captainbern.minecraft.protocol.PacketType;
import com.captainbern.minecraft.reflection.MinecraftMethods;
import com.captainbern.minecraft.wrapper.WrappedPacket;
import com.dsh105.commodus.GeneralUtil;
import com.dsh105.commodus.GeometryUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleBuilder implements Cloneable {

    private String name;
    private float x;
    private float y;
    private float z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float speed;
    private int amount;

    public ParticleBuilder(String name, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
    }

    public ParticleBuilder(String name, float x, float y, float z, float speed, int amount) {
        this(name, x, y, z, GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat(), speed, amount);
    }

    public ParticleBuilder(String name, float speed, int amount) {
        this(name, 0, 0, 0, GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat(), speed, amount);
    }

    public static ParticleBuilder build(Particle particle) {
        return new ParticleBuilder(particle.getName(), particle.getSpeed(), particle.getAmount());
    }

    public void show(Player player) {
        WrappedPacket packet = new WrappedPacket(PacketType.Play.Server.WORLD_PARTICLES);
        packet.getStrings().write(0, name);
        packet.getFloats().write(0, x);
        packet.getFloats().write(1, y);
        packet.getFloats().write(2, z);
        packet.getFloats().write(3, offsetX);
        packet.getFloats().write(4, offsetY);
        packet.getFloats().write(5, offsetZ);
        packet.getFloats().write(6, speed);
        packet.getIntegers().write(0, amount);
        MinecraftMethods.sendPacket(player, packet.getHandle());
    }

    public void show(Location origin) {
        for (Player player : GeometryUtil.getNearbyPlayers(origin, 50)) {
            show(player);
        }
    }

    public ParticleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ParticleBuilder withLocation(Vector vector) {
        this.x = (float) vector.getX();
        this.y = (float) vector.getY();
        this.z = (float) vector.getZ();
        return this;
    }

    public ParticleBuilder withX(float x) {
        this.x = x;
        return this;
    }

    public ParticleBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public ParticleBuilder withZ(float z) {
        this.z = z;
        return this;
    }

    public ParticleBuilder withOffset(Vector vector) {
        this.offsetX = (float) vector.getX();
        this.offsetY = (float) vector.getY();
        this.offsetZ = (float) vector.getZ();
        return this;
    }

    public ParticleBuilder withOffsetX(float offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    public ParticleBuilder withOffsetY(float offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    public ParticleBuilder withOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
        return this;
    }

    public ParticleBuilder withSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public ParticleBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location getLocation(World world) {
        return new Location(world, x, y, z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getSpeed() {
        return speed;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public ParticleBuilder clone() throws CloneNotSupportedException {
        return (ParticleBuilder) super.clone();
    }
}