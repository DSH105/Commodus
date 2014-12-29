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

package com.dsh105.commodus.bukkit;

import com.dsh105.commodus.GeneralUtil;
import org.bukkit.Material;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Information present in the mappings used by this class were obtained from http://minecraft-ids.grahamedgecombe.com/
 */
public final class ItemRegistry {

    // TODO: docs

    private static final Map<MaterialId, String> ID_TO_NAME_MAP = new HashMap<>();
    private static final Map<String, MaterialId> INVERSE_ID_TO_NAME_MAP = new HashMap<>();

    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ItemRegistry.class.getResourceAsStream("/items.tsv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] columns = line.split("\t");
                try {
                    ID_TO_NAME_MAP.put(MaterialId.of(GeneralUtil.toInteger(columns[0]), GeneralUtil.toInteger(columns[1])), columns[2]);
                } catch (NumberFormatException ignored) {
                    // carry on!
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        INVERSE_ID_TO_NAME_MAP.putAll(GeneralUtil.invertMap(ID_TO_NAME_MAP));
    }

    public static String getName(Material material) {
        return getName(material, 0);
    }

    public static String getName(Material material, int data) {
        return getName(material.getId(), data);
    }

    public static String getName(int id) {
        return getName(id, 0);
    }

    public static String getName(int id, int data) {
        return getName(MaterialId.of(id, data));
    }

    public static String getName(MaterialId materialId) {
        return ID_TO_NAME_MAP.get(materialId);
    }

    public static MaterialId getId(String name) {
        return INVERSE_ID_TO_NAME_MAP.get(name);
    }
}