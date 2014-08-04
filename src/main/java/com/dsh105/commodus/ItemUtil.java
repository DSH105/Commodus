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

/**
 * Utilities for item manipulation
 */
public class ItemUtil {

    private ItemUtil() {
    }

    /**
     * Builds a placeholder item, utilising the given {@code content} as the name and lore of the item
     * <p>
     * Not really useful, except for in the PowerMessage library (https://github.com/DSH105/PowerMessage)
     *
     * @param content content to include in the name and lore of the built item
     * @return an item built from the given {@code content}
     */
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
            throw new IllegalArgumentException("Item has no specified ID!");
        }

        if (itemAmount == -1) {
            throw new IllegalArgumentException("Item has no specified amount!");
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

        if (item.getType() == null) {
            throw new IllegalArgumentException("Item has null type!");
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
                if (enchant != null && enchant.getKey() != null) {
                    try {
                        item.addEnchantment(enchant.getKey(), enchant.getValue());
                    } catch (IllegalArgumentException ex) {
                        item.addUnsafeEnchantment(enchant.getKey(), enchant.getValue());
                    }
                } else {
                    throw new IllegalArgumentException("Enchantment is null!");
                }
            }
        }

        return item;
    }

    /**
     * Verify if two item stacks have the same contents
     * <p>
     * This means the lore and enchants can be in any order
     *
     * @param one the first ItemStack
     * @param two the second ItemStack
     * @return true if same
     */
    public static boolean areIdentical(ItemStack one, ItemStack two) {
        if (one != null && two != null) {
            if (one.getType() != two.getType()) {
                return false;
            }

            if (one.getDurability() != two.getDurability()) {
                return false;
            }

            if (one.hasItemMeta() != two.hasItemMeta()) {
                return false;
            }

            if (one.hasItemMeta() && two.hasItemMeta()) {
                if (!one.getItemMeta().getDisplayName().equals(two.getItemMeta().getDisplayName())) {
                    return false;
                }

                if (one.getItemMeta().getLore().size() != two.getItemMeta().getLore().size()) {
                    return false;
                }

                for (String lore : one.getItemMeta().getLore()) {
                    if (!two.getItemMeta().getLore().contains(lore)) {
                        return false;
                    }
                }
            }

            if (one.getEnchantments().size() != two.getEnchantments().size()) {
                return false;
            }

            for (Map.Entry<Enchantment, Integer> enchantment : one.getEnchantments().entrySet()) {
                if (two.getEnchantments().containsKey(enchantment.getKey())) {
                    int twoEnchantmentLevel = two.getEnchantments().get(enchantment.getKey());

                    if (twoEnchantmentLevel != enchantment.getValue()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}