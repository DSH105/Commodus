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

import com.dsh105.commodus.configuration.ConfigManager;
import com.dsh105.commodus.configuration.ConfigurationInput;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class BukkitConfigManager extends ConfigManager<BukkitConfig> {

    // TODO: docs

    private Plugin plugin;

    public BukkitConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected File getPluginDirectory() {
        return plugin.getDataFolder();
    }

    @Override
    protected BukkitConfig prepare(File file, ConfigurationInput configurationInput) {
        return new BukkitConfig(this, file, configurationInput.getInput(), configurationInput.getTotalComments());
    }
}