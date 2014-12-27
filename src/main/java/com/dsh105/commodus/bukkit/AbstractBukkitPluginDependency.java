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

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

/**
 * Provider for a Bukkit plugin dependency.
 *
 * @param <P> The plugin type provided by this dependency
 */
public abstract class AbstractBukkitPluginDependency<P extends Plugin> implements BukkitPluginDependency<P> {

    protected P dependency;
    protected String dependencyName;
    protected boolean hooked;
    private Plugin handlingPlugin;

    /**
     * Constructs a new plugin dependency container for the plugin of the given name
     *
     * @param handlingPlugin Plugin that is handling this dependency
     * @param dependencyName Name of the plugin dependency to retrieve
     * @throws java.lang.IllegalArgumentException if retrieval of the plugin with the given name is not of the type
     *                                            specified by {@code P}
     */
    public AbstractBukkitPluginDependency(Plugin handlingPlugin, String dependencyName) {
        this(handlingPlugin, Bukkit.getPluginManager().getPlugin(dependencyName));
        this.dependencyName = dependencyName;
    }

    /**
     * Constructs a new plugin dependency container for the given plugin
     *
     * @param handlingPlugin Plugin that is handling this dependency
     * @param dependency     Plugin dependency instance
     */
    public AbstractBukkitPluginDependency(Plugin handlingPlugin, Plugin dependency) {
        this.handlingPlugin = handlingPlugin;
        confirmHook(dependency);
        prepareListeners();
    }

    private void prepareListeners() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            protected void onPluginEnable(PluginEnableEvent event) {
                if (dependency == null && getDependencyName().equals(event.getPlugin().getName())) {
                    confirmHook(event.getPlugin());
                }
            }

            @EventHandler(priority = EventPriority.MONITOR)
            protected void onPluginDisable(PluginDisableEvent event) {
                if (dependency != null && getDependencyName().equals(event.getPlugin().getName())) {
                    hooked = false;
                    onUnhook();
                    dependency = null;
                }
            }
        }, this.handlingPlugin);
    }

    private void confirmHook(Plugin plugin) {
        try {
            this.dependency = (P) plugin;
        } catch (ClassCastException ignored) {
        }

        if (this.dependency != null && this.dependency.isEnabled()) {
            if (this.dependencyName == null) {
                this.dependencyName = dependency.getName();
            }
            this.hooked = true;
            this.onHook();
            this.handlingPlugin.getLogger().info("Successfully hooked: " + this.dependencyName);
            return;
        }
        throw new IllegalArgumentException("Failed to provide dependency hook for " + dependencyName + " (Is it enabled and is the given type correct?)");
    }

    @Override
    public Plugin getHandlingPlugin() {
        return handlingPlugin;
    }

    @Override
    public P getDependency() {
        return dependency;
    }

    @Override
    public String getDependencyName() {
        return dependencyName;
    }

    @Override
    public boolean isHooked() {
        return hooked;
    }
}