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

import org.bukkit.entity.Player;

public enum Particle {

    /*
     * Updated according to the latest Minecraft snapshot list
     * (in order)
     */
    DEATH_CLOUD("explode", 0.1F, 10),
    EXPLOSION("largeexplode", 0.1F, 1),
    HUGE_EXPLOSION("hugeexplosion", 0.1F, 1),
    FIREWORKS_SPARK("fireworksSpark", 0.1F, 50),
    BUBBLE("bubble", 0.4F, 50),
    SPLASH("splash", 1F, 40),
    WAKE("wake", 0.1F, 50),
    SUSPENDED("wake", 0F, 50),
    DEPTH_SUSPEND("depthSuspend", 0F, 100),
    CRITICAL("crit", 0.1F, 100),
    MAGIC_CRITICAL("magicCrit", 0.1F, 100),
    SMALL_SMOKE("smoke", 0.05F, 100),
    SMOKE("largesmoke", 0.2F, 20),
    INSTANT_SPELL("instantSpell", 0.05F, 50),
    SPELL_AMBIENT("mobSpellAmbient", 1F, 100),
    SPELL("mobSpell", 1F, 100),
    WITCH_MAGIC("witchMagic", 1F, 20),
    WATER_DRIP("dripWater", 0F, 100),
    LAVA_DRIP("dripLava", 0F, 100),
    ANGRY_VILLAGER("angryVillager", 0F, 20),
    SPARKLE("happyVillager", 0F, 20),
    VOID("townaura", 1F, 100),
    NOTE("note", 1F, 1),
    PORTAL("largesmoke", 1F, 100),
    MAGIC_RUNES("enchantmenttable", 1F, 100),
    FIRE("flame", 0.05F, 100),
    LAVA_SPARK("lava", 0F, 4),
    FOOTSTEP("footstep", 0F, 10),
    CLOUD("cloud", 0.1F, 50),
    RED_SMOKE("reddust", 0F, 40),
    RAINBOW_SMOKE("reddust", 1F, 100),
    SNOWBALL("snowballpoof", 1F, 20),
    SNOW_SHOVEL("snowshovel", 0.02F, 30),
    SLIME_SPLAT("slime", 1F, 30),
    HEART("heart", 0F, 4),
    BARRIER("barrier", 0F, 1),
    GUARDIAN_APPEARANCE("mobappearance", 0F, 1);

    private String name;
    private float speed;
    private int amount;

    Particle(String name, float speed, int amount) {
        this.name = name;
        this.speed = speed;
        this.amount = amount;
    }

    public ParticleBuilder builder() {
        return new ParticleBuilder(this.getName(), this.getSpeed(), this.getAmount());
    }

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
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public enum Data {
        BLOCK_BREAK("blockcrack", 0.1F, 100),
        BLOCK_DUST("blockdust", 0.1F, 100);

        private String name;
        private float speed;
        private int amount;

        Data(String name, float speed, int amount) {
            this.name = name;
            this.speed = speed;
            this.amount = amount;
        }

        public DataParticleBuilder builder() {
            return new DataParticleBuilder(this.getName(), this.getSpeed(), this.getAmount());
        }

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
                if (type.getName().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }
    }
}