package com.dsh105.commodus;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class EnchantmentUtil {
    private HashMap<String, Enchantment> enchantmentAsscoations = new HashMap<>();

    /**
     * Load the most common key words
     */
    public void load() {
        addValue(Enchantment.OXYGEN, "Respiration", "Underwater");
        addValue(Enchantment.WATER_WORKER, "Aqua Affinity", "Underwater Mine", "Underwater Mining");
        addValue(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
        addValue(Enchantment.PROTECTION_FIRE, "Fire Protection");
        addValue(Enchantment.PROTECTION_FALL, "Feather Falling", "Fall", "Feather", "Falling");
        addValue(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection", "Blast");
        addValue(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
        addValue(Enchantment.DAMAGE_ALL, "Sharpness");
        addValue(Enchantment.DAMAGE_ARTHROPODS, "Smite");
        addValue(Enchantment.LOOT_BONUS_MOBS, "Looting");
        addValue(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods", "Arthropods", "Bane");
        addValue(Enchantment.DIG_SPEED, "Efficiency");
        addValue(Enchantment.DURABILITY, "Unbreaking");
        addValue(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
        addValue(Enchantment.ARROW_DAMAGE, "Power");
        addValue(Enchantment.ARROW_KNOCKBACK, "Punch");
        addValue(Enchantment.ARROW_FIRE, "Flame");
        addValue(Enchantment.ARROW_INFINITE, "Infinity");
        addValue(Enchantment.LUCK, "Luck of the Sea");
        addValue(Enchantment.ARROW_INFINITE, "Infinity");
    }

    /**
     * Add a new set of values for conversion
     *
     * @param enchant matching {@link org.bukkit.enchantments.Enchantment}
     * @param value String list to be used in conversion as key words
     */
    public void addValue(Enchantment enchant, String... value) {
        for (String s : value) {
            enchantmentAsscoations.put(s.toLowerCase().replace(" ", ""), enchant);
        }
    }

    /**
     * Attempt to match a String to an Enchantment
     *
     * @param input String to be matched to a valid {@link org.bukkit.enchantments.Enchantment}
     * @return null if no matching {@link org.bukkit.enchantments.Enchantment} is found
     */
    public Enchantment getEnchantmentFromName(String input) {
        if (input == null) {
            return null;
        }

        input = input.toLowerCase().replace(" ", "");

        if (enchantmentAsscoations.containsKey(input)) {
            return enchantmentAsscoations.get(input);
        } else {
            return Enchantment.getByName(input);
        }
    }
}
    