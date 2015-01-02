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

import com.dsh105.commodus.ServerUtil;
import com.dsh105.commodus.sponge.SpongeUtil;
import com.google.common.base.Optional;
import org.bukkit.Bukkit;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class EntityContainer extends Container<EntityContainer> {

    private UUID worldUID;
    private UUID entityUID;

    private EntityContainer(UUID worldUID, UUID entityUID) {
        this.worldUID = worldUID;
        this.entityUID = entityUID;
    }
    
    public static EntityContainer of(org.bukkit.entity.Entity entity) {
        return new EntityContainer(entity.getWorld().getUID(), entity.getUniqueId());
    }

    public static EntityContainer of(org.spongepowered.api.entity.Entity entity) {
        return new EntityContainer(entity.getWorld().getUniqueID(), entity.getUniqueId());
    }
    
    public org.bukkit.entity.Entity asBukkit() {
        for (org.bukkit.entity.Entity entity : Bukkit.getWorld(worldUID).getEntities()) {
            if (entity.getUniqueId().equals(entityUID)) {
                return entity;
            }
        }
        throw new IllegalStateException(String.format("Entity is no longer present (ID=%s)", entityUID));
    }

    public org.spongepowered.api.entity.Entity asSponge() {
        Optional<World> world = SpongeUtil.getGame().getServer().get().getWorld(worldUID);
        if (!world.isPresent()) {
            throw new IllegalStateException(String.format("World is no longer present (ID=%s)", worldUID));
        }
        Optional<org.spongepowered.api.entity.Entity> entity = world.get().getEntityFromUUID(entityUID);
        if (!entity.isPresent()) {
            throw new IllegalStateException(String.format("Entity is no longer present (ID=%s)", entityUID));
        }
        return entity.get();
    }
    
    public String getName() {
        switch (ServerUtil.getServerBrand().getCapsule()) {
            case BUKKIT:
                return asBukkit().getCustomName();
            case SPONGE:
                org.spongepowered.api.entity.Entity entity = asSponge();
                if (entity instanceof Living) {
                    return ((Living) entity).getCustomName();
                }
                return "";
        }
        throw new IllegalStateException("Entity must be either a Bukkit or Sponge entity");
    }
    
    public UUID getUID() {
        return entityUID;
    }
    
    public String getTypeId() {
        switch (ServerUtil.getServerBrand().getCapsule()) {
            case BUKKIT:
                return asBukkit().getType().getName();
            case SPONGE:
                return asSponge().getType().getId();
        }
        throw new IllegalStateException("Entity must be either a Bukkit or Sponge entity");
    }
}