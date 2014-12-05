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

public enum Particle {

    DEATH_CLOUD(0, "explode", 0.1F, 10),
    EXPLOSION(1, "largeexplode", 0.1F, 1),
    HUGE_EXPLOSION(2, "hugeexplosion", 0.1F, 1),
    FIREWORKS_SPARK(3, "fireworksSpark", 0.1F, 50),
    BUBBLE(4, "bubble", 0.4F, 50),
    SPLASH(5, "splash", 1F, 40),
    WAKE(6, "wake", 0.1F, 50),
    SUSPENDED(7, "suspended", 0F, 50),
    DEPTH_SUSPEND(8, "depthsuspend", 0F, 100),
    CRITICAL(9, "crit", 0.1F, 100),
    MAGIC_CRITICAL(10, "magicCrit", 0.1F, 100),
    SMALL_SMOKE(11, "smoke", 0.05F, 100),
    SMOKE(12, "largesmoke", 0.2F, 20),
    SPELL(13, "spell", 0.05F, 50),
    INSTANT_SPELL(14, "instantSpell", 0.05F, 50),
    SPELL_MOB(15, "mobSpell", 1F, 100),
    SPELL_MOB_AMBIENT(16, "mobSpellAmbient", 1F, 100),
    WITCH_MAGIC(17, "witchMagic", 1F, 20),
    WATER_DRIP(18, "dripWater", 0F, 100),
    LAVA_DRIP(19, "dripLava", 0F, 100),
    ANGRY_VILLAGER(20, "angryVillager", 0F, 20),
    SPARKLE(21, "happyVillager", 0F, 20),
    VOID(22, "townaura", 1F, 100),
    NOTE(23, "note", 1F, 1),
    PORTAL(24, "portal", 1F, 100),
    MAGIC_RUNES(25, "enchantmenttable", 1F, 100),
    FIRE(26, "flame", 0.05F, 100),
    LAVA_SPARK(27, "lava", 0F, 4),
    FOOTSTEP(28, "footstep", 0F, 10),
    CLOUD(29, "cloud", 0.1F, 50),
    RED_SMOKE(30, "reddust", 0F, 40),
    RAINBOW_SMOKE(30, "reddust", 1F, 100),
    SNOWBALL(32, "snowballpoof", 1F, 20),
    SNOW_SHOVEL(32, "snowshovel", 0.02F, 30),
    SLIME_SPLAT(33, "slime", 1F, 30),
    HEART(34, "heart", 0F, 4),
    BARRIER(35, "barrier", 0F, 1),
    WATER_DROPLET(39, "droplet", 0.05F, 10),
    ITEM_TAKE(40, "take", 0F, 1),
    GUARDIAN_APPEARANCE(41, "mobappearance", 0F, 1),

    ICON_BREAK(36, "iconcrack", 0.1F, 100),
    BLOCK_BREAK(37, "blockcrack", 0.1F, 100),
    BLOCK_DUST(38, "blockdust", 0.1F, 100);

    private int id;
    private String name;
    private float speed;
    private int amount;

    Particle(int id, String name, float speed, int amount) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.amount = amount;
    }

    public ParticleBuilder builder() {
        return new ParticleBuilder(this, speed, amount);
    }

    public int getId() {
        return id;
    }

    @Deprecated // use getId()
    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public int getAmount() {
        return amount;
    }

    public static Particle fromName(String name) {
        for (Particle type : Particle.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}