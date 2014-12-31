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
    private static final Map<String, Integer> NAME_TO_ID_MAP = new HashMap<>();

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
                    int id = GeneralUtil.toInteger(columns[0]);
                    int meta = GeneralUtil.toInteger(columns[1]);
                    String name = columns[2];
                    ID_TO_NAME_MAP.put(MaterialId.of(id, meta), name);
                    if (!NAME_TO_ID_MAP.containsKey(name)) {
                        NAME_TO_ID_MAP.put(name, id);
                    }
                } catch (NumberFormatException ignored) {
                    // carry on!
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getId(Material material) {
        return getId(material, 0);
    }

    public static String getId(Material material, int data) {
        return getId(material.getId(), data);
    }

    public static String getId(int id) {
        return getId(id, 0);
    }

    public static String getId(int id, int data) {
        return getId(MaterialId.of(id, data));
    }

    public static String getId(MaterialId materialId) {
        return ID_TO_NAME_MAP.get(materialId);
    }

    public static MaterialId getId(String name) {
        return getId(name, 0);
    }

    public static MaterialId getId(String name, int meta) {
        return MaterialId.of(NAME_TO_ID_MAP.get(name), meta);
    }
}