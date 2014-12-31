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

import org.bukkit.Material;

public class MaterialId {

    // TODO: docs

    private int id;
    private int meta;

    private MaterialId(int id, int meta) {
        this.id = id;
        this.meta = meta;
    }

    public static MaterialId of(int id) {
        return of(id, 0);
    }

    public static MaterialId of(int id, int data) {
        return new MaterialId(id, data);
    }

    public static MaterialId of(Material material, int data) {
        return of(material.getId(), data);
    }

    public static MaterialId of(Material material) {
        return of(material, 0);
    }

    public int getId() {
        return id;
    }

    public int getMeta() {
        return meta;
    }
}