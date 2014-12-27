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

package com.dsh105.commodus.sponge;

import com.dsh105.commodus.configuration.ConfigManager;
import com.dsh105.commodus.configuration.ConfigurationInput;

import java.io.File;

// TODO: docs
public class SpongeConfigManager extends ConfigManager<SpongeConfig> {

    private File pluginDirectory;

    public SpongeConfigManager(File pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    @Override
    protected File getPluginDirectory() {
        return pluginDirectory;
    }

    @Override
    protected SpongeConfig prepare(File file, ConfigurationInput configurationInput) {
        return new SpongeConfig(this, file, configurationInput.getContent(), configurationInput.getTotalComments());
    }
}