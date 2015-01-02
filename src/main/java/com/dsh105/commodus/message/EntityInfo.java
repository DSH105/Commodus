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

package com.dsh105.commodus.message;

import com.dsh105.commodus.container.EntityContainer;
import com.dsh105.commodus.sponge.SpongeUtil;
import com.google.common.base.Optional;
import org.spongepowered.api.entity.living.Living;

import java.util.UUID;

// used only for HoverAction.ShowEntity
public class EntityInfo {

    private org.spongepowered.api.entity.Entity spongeEntity;
    private UUID entityUID;
    private String entityName;
    private String entityTypeId;

    protected EntityInfo(UUID entityUID, String entityName, String entityTypeId) {
        this.entityUID = entityUID;
        this.entityName = entityName;
        this.entityTypeId = entityTypeId;
    }

    public static EntityInfo of(org.bukkit.entity.Entity entity) {
        return new EntityInfo(entity.getUniqueId(), entity.getCustomName(), entity.getType().getName());
    }

    public static EntityInfo of(org.spongepowered.api.entity.Entity entity) {
        String name = entity instanceof Living ? ((Living) entity).getCustomName() : "";
        EntityInfo entityInfo = new EntityInfo(entity.getUniqueId(), name, entity.getType().getId());
        entityInfo.spongeEntity = entity;
        return entityInfo;
    }
    
    // not exactly a short process
    public org.spongepowered.api.entity.Entity asSponge() {
        if (spongeEntity != null) {
            return spongeEntity;
        }
        for (org.spongepowered.api.world.World world : SpongeUtil.getGame().getServer().get().getWorlds()) {
            Optional<org.spongepowered.api.entity.Entity> entity = world.getEntityFromUUID(entityUID);
            if (entity.isPresent()) {
                return entity.get();
            }
        }
        return null;
    }

    public UUID getEntityUID() {
        return entityUID;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }
}