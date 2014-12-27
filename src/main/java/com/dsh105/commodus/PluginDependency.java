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

/**
 * Represents a container for a Minecraft plugin dependency
 *
 * @param <P> the type of plugin hooked
 */
public interface PluginDependency<H, P> {

    /**
     * Called when this plugin is hooked.
     * <p/>
     * This method is not necessarily called when the handling plugin starts up, but rather when the dependency is
     * enabled on the server. It may also be called multiple times in conjunction with {@link #onUnhook()} if the plugin
     * is enabled and disabled during the lifetime of the server.
     */
    void onHook();

    /**
     * Called when this plugin is hooked.
     * <p/>
     * This may be called multiple times in conjunction with {@link #onHook()} if the plugin is enabled and disabled
     * during the lifetime of the server.
     */
    void onUnhook();

    /**
     * Gets the plugin handling this dependency
     *
     * @return Plugin handling this dependency
     */
    H getHandlingPlugin();

    /**
     * Gets the plugin being depended on
     *
     * @return Plugin dependency
     */
    P getDependency();

    /**
     * Gets the name of the dependency
     *
     * @return Dependency name
     */
    String getDependencyName();

    /**
     * Gets whether the plugin has been successfully hooked into
     *
     * @return True if the plugin has been hooked successfully
     */
    boolean isHooked();
}