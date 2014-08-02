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

package com.dsh105.commodus.dependency;

import org.bukkit.plugin.Plugin;

/**
 * Represents a dependency provider for a particular plugin
 *
 * @param <T> plugin represented by this provider
 */
public interface PluginDependencyProvider<T extends Plugin> {

    /**
     * Gets the plugin being depended upon
     *
     * @return plugin dependency
     */
    public T getDependency();

    /**
     * Gets whether the plugin has been successfully hooked into
     *
     * @return true if the plugin has been hooked successfully
     */
    public boolean isHooked();

    /**
     * Gets the plugin handling this dependency
     *
     * @return plugin handling this dependency
     */
    public Plugin getHandlingPlugin();

    /**
     * Gets the name of the dependency
     *
     * @return dependency name
     */
    public String getDependencyName();
}