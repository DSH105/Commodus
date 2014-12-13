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
import com.dsh105.commodus.ServerUtil;
import com.dsh105.commodus.Version;
import com.dsh105.commodus.reflection.Reflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParticleBuilder implements Cloneable {

    private Particle type;
    private Location position;
    private Vector offset;
    private float speed;
    private int amount;
    private boolean force; // force display
    private int[] data = new int[0];

    @Deprecated
    public ParticleBuilder(String name, float speed, int amount) {
        this(Particle.fromName(name), speed, amount);
    }

    public ParticleBuilder(Particle type, float speed, int amount) {
        this.ofType(type)
                .offset(GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat(), GeneralUtil.random().nextFloat())
                .atSpeed(speed)
                .ofAmount(amount);
    }

    public static ParticleBuilder build(Particle particle) {
        return new ParticleBuilder(particle.getName(), particle.getSpeed(), particle.getAmount());
    }

    public void show(Player player) {
        WrappedPacket packet = new WrappedPacket(PacketType.Play.Server.WORLD_PARTICLES);
        packet.getAccessor().write(0, getNMSParticleType());
        packet.getFloats().write(0, (float) position.getX());
        packet.getFloats().write(1, (float) position.getY());
        packet.getFloats().write(2, (float) position.getZ());
        packet.getFloats().write(3, (float) offset.getX());
        packet.getFloats().write(4, (float) offset.getY());
        packet.getFloats().write(5, (float) offset.getZ());
        packet.getFloats().write(6, speed);
        packet.getIntegers().write(0, amount);
        if (new Version().isCompatible("1.8")) {
            packet.getIntegerArrays().write(0, data);
            packet.getBooleans().write(0, this.force); // TODO
        }
        ServerUtil.sendPacket(packet.getHandle(), player);
    }

    private Object getNMSParticleType() {
        if (new Version().isCompatible("1.8")) {
            Class<?> enumParticle = Reflection.getNMSClass("EnumParticle");
            return enumParticle.getEnumConstants()[type.getId()];
        }
        String name = type.getName();
        for (int i : data) {
            name += "_" + i;
        }
        return name;
    }

    public void show() {
        for (Player player : GeometryUtil.getNearbyPlayers(position, 50)) {
            show(player);
        }
    }

    public ParticleBuilder ofType(Particle type) {
        this.type = type;
        return this;
    }

    public ParticleBuilder at(Location position) {
        this.position = position.clone();
        return this;
    }

    public ParticleBuilder at(World world, float x, float y, float z) {
        this.position = new Location(world, x, y, z);
        return this;
    }

    public ParticleBuilder offset(Vector offset) {
        this.offset = offset.clone();
        return this;
    }

    public ParticleBuilder offset(float x, float y, float z) {
        this.offset(new Vector(x, y, z));
        return this;
    }

    public ParticleBuilder atSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public ParticleBuilder ofAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ParticleBuilder withData(int... data) {
        this.data = data != null ? data : new int[0];
        return this;
    }

    public ParticleBuilder ofBlockType(Material material) {
        return this.ofBlockType(material, 0);
    }

    public ParticleBuilder ofBlockType(Material material, int metadata) {
        this.data = new int[]{material.getId(), metadata};
        return this;
    }

    public Particle getType() {
        return type;
    }

    public Location getPosition() {
        return position;
    }

    public Vector getOffset() {
        return offset;
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