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

public class DataParticleBuilder extends ParticleBuilder {

    private int blockId;
    private int metaId;

    public DataParticleBuilder(String name, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount, int blockId, int metaId) {
        super(name, x, y, z, offsetX, offsetY, offsetZ, speed, amount);
        this.blockId = blockId;
        this.metaId = metaId;
    }

    public DataParticleBuilder(String name, float x, float y, float z, float speed, int amount, int blockId, int metaId) {
        super(name, x, y, z, speed, amount);
        this.blockId = blockId;
        this.metaId = metaId;
    }

    public DataParticleBuilder(String name, float speed, int amount, int blockId, int metaId) {
        super(name, speed, amount);
        this.blockId = blockId;
        this.metaId = metaId;
    }

    public DataParticleBuilder(String name, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        super(name, x, y, z, offsetX, offsetY, offsetZ, speed, amount);
    }

    public DataParticleBuilder(String name, float x, float y, float z, float speed, int amount) {
        super(name, x, y, z, speed, amount);
    }

    public DataParticleBuilder(String name, float speed, int amount) {
        super(name, speed, amount);
    }

    public static DataParticleBuilder build(Particle particle) {
        return build(particle, 0);
    }

    public static DataParticleBuilder build(Particle particle, int blockId) {
        return build(particle, blockId, 0);
    }

    public static DataParticleBuilder build(Particle particle, int blockId, int metaId) {
        return new DataParticleBuilder(particle.getName(), particle.getSpeed(), particle.getAmount(), blockId, metaId);
    }

    public DataParticleBuilder withBlockId(int blockId) {
        this.blockId = blockId;
        this.setName(getName() + "_" + this.blockId + "_" + metaId);
        return this;
    }

    public DataParticleBuilder withMetaId(int metaId) {
        this.metaId = metaId;
        this.setName(getName() + "_" + blockId + "_" + this.metaId);
        return this;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getMetaId() {
        return metaId;
    }
}