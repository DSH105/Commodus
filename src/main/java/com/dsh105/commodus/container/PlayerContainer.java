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
import com.dsh105.commodus.StringUtil;
import com.dsh105.commodus.bukkit.BukkitIdentUtil;
import com.dsh105.commodus.message.Message;
import com.dsh105.commodus.sponge.SpongeIdentUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerContainer extends Container<PlayerContainer> {

    // TODO: docs

    private static final Map<UUID, PlayerContainer> PLAYER_BRIDGES = new HashMap<>();

    private UUID playerUID;

    protected PlayerContainer(UUID playerUID) {
        this.playerUID = playerUID;
    }

    public static PlayerContainer of(UUID playerUID) {
        PlayerContainer bridge = PLAYER_BRIDGES.get(playerUID);
        if (bridge == null) {
            bridge = new PlayerContainer(playerUID);
            PLAYER_BRIDGES.put(playerUID, bridge);
        }
        return bridge;
    }

    public static PlayerContainer of(org.bukkit.entity.Player bukkitPlayer) {
        return of(StringUtil.convertUUID(BukkitIdentUtil.getIdent(bukkitPlayer)));
    }

    public static PlayerContainer of(org.spongepowered.api.entity.player.Player spongePlayer) {
        return of(SpongeIdentUtil.getIdent(spongePlayer));
    }

    public UUID getUID() {
        return playerUID;
    }

    public boolean is(UUID playerUID) {
        return this.playerUID.equals(playerUID);
    }

    public boolean is(PlayerContainer player) {
        return playerUID.equals(player.playerUID);
    }

    public Object get() {
        switch (ServerUtil.getServerBrand()) {
            case BUKKIT:
            case SPIGOT:
            case CAULDRON:
                return BukkitIdentUtil.getPlayer(playerUID);
            case SPONGE:
                return SpongeIdentUtil.getPlayer(playerUID);
            default:
                return null;
        }
    }

    public org.bukkit.entity.Player asBukkit() {
        return BukkitIdentUtil.getPlayer(playerUID);
    }

    public org.spongepowered.api.entity.player.Player asSponge() {
        return SpongeIdentUtil.getPlayer(playerUID);
    }
    
    public void sendMessage(Message... messages) {
        for (Message message : messages) {
            sendMessage(message);
        }
    }
    
    public void sendMessage(Message message) {
        message.send(this);
    }
}