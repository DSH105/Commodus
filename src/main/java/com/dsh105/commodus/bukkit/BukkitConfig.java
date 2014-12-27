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

import com.dsh105.commodus.configuration.AbstractConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Set;

public class BukkitConfig extends AbstractConfig {

    // TODO: docs

    private FileConfiguration configuration;

    public BukkitConfig(BukkitConfigManager manager, File file, InputStream configInput, int totalComments) {
        this(manager, file, load(configInput), totalComments);
    }

    public BukkitConfig(BukkitConfigManager manager, File file, FileConfiguration configuration, int totalComments) {
        super(manager, file, totalComments);
        this.configuration = configuration;
    }

    private static FileConfiguration load(InputStream configInput) {
        try {
            return YamlConfiguration.loadConfiguration(new InputStreamReader(configInput, "UTF-8"));
        } catch (NoSuchMethodError | UnsupportedEncodingException e) {
            return YamlConfiguration.loadConfiguration(configInput);
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String saveToString() {
        return configuration.saveToString();
    }

    @Override
    public void reloadConfig() throws IOException {
        configuration = load(getManager().compile(this).getInput());
    }

    @Override
    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

    @Override
    public Object getObject(String path) {
        return configuration.get(path);
    }
}