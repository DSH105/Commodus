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

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

/**
 * Needs some optimization
 */
public abstract class PluginDependencyProviderBase<T extends Plugin> implements PluginDependencyProvider<T> {

    protected PluginDependencyProviderBase<T> instance;
    private T dependency;
    protected boolean hooked;
    private Plugin myPluginInstance;
    private String dependencyName;

    // TODO: add more utils, plugin stuff mostly.

    public PluginDependencyProviderBase(Plugin myPluginInstance, String dependencyName) {
        this.instance = this;
        this.myPluginInstance = myPluginInstance;
        this.dependencyName = dependencyName;

        if (dependency == null && !this.hooked) {
            try {
                dependency = (T) Bukkit.getPluginManager().getPlugin(getDependencyName());

                if (this.dependency != null && this.dependency.isEnabled()) {
                    this.hooked = true;
                    onHook();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            protected void onEnable(PluginEnableEvent event) {
                if ((dependency == null) && (event.getPlugin().getName().equalsIgnoreCase(getDependencyName()))) {
                    try {
                        dependency = (T) event.getPlugin();
                        hooked = true;
                        onHook();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to hook plugin: " + event.getPlugin().getName());
                    }
                }
            }

            @EventHandler
            protected void onDisable(PluginDisableEvent event) {
                if ((dependency != null) && (event.getPlugin().getName().equalsIgnoreCase(getDependencyName()))) {
                    dependency = null;
                    hooked = false;
                    onUnhook();
                }
            }
        }, getHandlingPlugin());
    }

    public abstract void onHook();

    public abstract void onUnhook();

    @Override
    public T getDependency() {
        if (this.dependency == null) {
            throw new RuntimeException("Dependency is NULL!");
        }
        return this.dependency;
    }

    @Override
    public boolean isHooked() {
        return this.hooked;
    }

    @Override
    public Plugin getHandlingPlugin() {
        if (this.myPluginInstance == null) {
            throw new RuntimeException("HandlingPlugin is NULL!");
        }
        return this.myPluginInstance;
    }

    @Override
    public String getDependencyName() {
        if (this.dependencyName == null) {
            throw new RuntimeException("Dependency name is NULL!");
        }
        return this.dependencyName;
    }
}
