package com.dsh105.commodus;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Utilities for managing item enchantments
 */
public class EnchantmentUtil {

    private EnchantmentUtil() {
    }

    private static HashMap<String, Enchantment> enchantmentAssociations = new HashMap<>();
    private static Pattern REPLACE_FORMAT = Pattern.compile("[\\s_]");

    static {
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

        for (Enchantment e : Enchantment.values()) {
            addValue(e, e.getName());
        }
    }

    /**
     * Add a new set of values for conversion
     *
     * @param enchant matching {@link org.bukkit.enchantments.Enchantment}
     * @param value   String list to be used in conversion as key words
     */
    public static void addValue(Enchantment enchant, String... value) {
        for (String s : value) {
            enchantmentAssociations.put(s.toLowerCase().replaceAll(REPLACE_FORMAT.pattern(), ""), enchant);
        }
    }

    /**
     * Attempt to match a String to an Enchantment
     *
     * @param input String to be matched to a valid {@link org.bukkit.enchantments.Enchantment}
     * @return null if no matching {@link org.bukkit.enchantments.Enchantment} is found
     */
    public static Enchantment getEnchantmentFromName(String input) {
        if (input == null) {
            return null;
        }

        input = input.toLowerCase().replaceAll(REPLACE_FORMAT.pattern(), "");

        if (enchantmentAssociations.containsKey(input)) {
            return enchantmentAssociations.get(input);
        } else {
            return Enchantment.getByName(input);
        }
    }
}
    