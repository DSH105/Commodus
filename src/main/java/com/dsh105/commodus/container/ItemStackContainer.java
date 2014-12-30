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

import com.dsh105.commodus.bukkit.ItemRegistry;
import com.dsh105.commodus.bukkit.MaterialId;
import com.dsh105.commodus.sponge.SpongeUtil;
import org.bukkit.Material;
import org.spongepowered.api.GameRegistry;

public class ItemStackContainer extends Container<ItemStackContainer> {

    // TODO: docs
    // TODO: enchantments

    private String id;
    private int quantity;

    private ItemStackContainer(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public static ItemStackContainer of(String id, int quantity) {
        return new ItemStackContainer(id, quantity);
    }

    public static ItemStackContainer of(MaterialId id, int quantity) {
        return of(ItemRegistry.getName(id), quantity);
    }

    public static ItemStackContainer of(org.bukkit.Material bukkitMaterial, int quantity) {
        return of(bukkitMaterial, 0, quantity);
    }

    public static ItemStackContainer of(Material bukkitMaterial, int materialData, int quantity) {
        return of(ItemRegistry.getName(bukkitMaterial, materialData), quantity);
    }

    public static ItemStackContainer of(org.bukkit.inventory.ItemStack bukkitItemStack) {
        return of(bukkitItemStack.getType(), bukkitItemStack.getDurability(), bukkitItemStack.getAmount());
    }

    public static ItemStackContainer of(org.spongepowered.api.item.ItemType spongeType, int quantity) {
        return of(spongeType.getId(), quantity);
    }

    public static ItemStackContainer of(org.spongepowered.api.item.inventory.ItemStack spongeItemStack) {
        return of(spongeItemStack.getItem(), spongeItemStack.getQuantity());
    }

    public org.bukkit.inventory.ItemStack asBukkit() {
        MaterialId id = ItemRegistry.getId(this.id);
        return new org.bukkit.inventory.ItemStack(id.getId(), quantity, (short) id.getData());
    }

    public org.spongepowered.api.item.inventory.ItemStack asSponge() {
        GameRegistry gameRegistry = SpongeUtil.getGame().getRegistry();
        return gameRegistry.getItemBuilder().itemType(gameRegistry.getItem(id).get()).quantity(quantity).build();
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isSimilar(ItemStackContainer stack) {
        return stack != null && (stack == this || id.equals(stack.id) && quantity == stack.quantity);
    }
}