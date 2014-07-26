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

package com.dsh105.commodus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {

    private ItemUtil() {
    }

    public static ItemStack getItem(String... content) {
        ItemStack i = new ItemStack(Material.SNOW, 1, (short) 0);
        ItemMeta meta = i.getItemMeta();
        if (meta != null) {
            if (content.length > 0) {
                meta.setDisplayName(content[0]);
            }
            if (content.length > 1) {
                ArrayList<String> list = new ArrayList<String>();
                for (int index = 1; index < content.length; index++) {
                    list.add(ChatColor.WHITE + content[index]);
                }
                meta.setLore(list);
            }
            i.setItemMeta(meta);
        }
        return i;
    }

    public static ItemStack getItem(ConfigurationSection section) {
        String itemName = ChatColor.translateAlternateColorCodes('&', section.getString("name", null));
        int itemID = section.getInt("id", -1);
        int itemAmount = section.getInt("amount", 1);
        Byte itemData = (byte) section.getInt("data", -1);

	    if (itemID == -1) {
		    throw new NullPointerException("Item has no specified ID!");
	    }

	    if (itemAmount == -1) {
		    throw new NullPointerException("Item has no specified amount!");
	    }

        HashMap<Enchantment, Integer> itemEnchantments = new HashMap<>();
        for (String id : section.getStringList("enchants")) {
            try {
                Enchantment e = EnchantmentUtil.getEnchantmentFromName(id.split(":")[0]);

                if (e != null) {
                    itemEnchantments.put(e, GeneralUtil.toInteger(id.split(":")[1]));
                }
            } catch (IndexOutOfBoundsException | NumberFormatException ignored) {

            }
        }

        List<String> itemLore = new ArrayList<>();
        for (String l : section.getStringList("lore")) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', l));
        }

        ItemStack item;
        if (itemData > 0) {
            item = new ItemStack(Material.getMaterial(itemID), itemAmount, itemData);
        } else {
            item = new ItemStack(Material.getMaterial(itemID), itemAmount);
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemName != null && !itemName.isEmpty()) {
            itemMeta.setDisplayName(itemName);
        }

        if (!itemLore.isEmpty()) {
            itemMeta.setLore(itemLore);
        }

        item.setItemMeta(itemMeta);

        if (!itemEnchantments.isEmpty()) {
            for (Map.Entry<Enchantment, Integer> enchant : itemEnchantments.entrySet()) {
                try {
                    item.addEnchantment(enchant.getKey(), enchant.getValue());
                } catch (IllegalArgumentException ex) {
                    item.addUnsafeEnchantment(enchant.getKey(), enchant.getValue());
                }
            }
        }

        return item;
    }
}