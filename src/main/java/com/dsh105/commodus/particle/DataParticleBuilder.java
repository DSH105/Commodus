package com.dsh105.commodus.particle;

import org.bukkit.entity.Player;

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

    public DataParticleBuilder withBlockId(int blockId) {
        this.blockId = blockId;
        this.setName(getName() + "_" + getBlockId() + "_" + getMetaId());
        return this;
    }

    public DataParticleBuilder withMetaId(int metaId) {
        this.metaId = metaId;
        this.setName(getName() + "_" + getBlockId() + "_" + getMetaId());
        return this;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getMetaId() {
        return metaId;
    }
}