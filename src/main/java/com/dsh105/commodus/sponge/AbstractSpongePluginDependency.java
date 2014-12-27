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

import org.spongepowered.api.event.state.PostInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.event.Order;
import org.spongepowered.api.util.event.Subscribe;

/**
 * Provider for a Bukkit plugin dependency.
 */
public abstract class AbstractSpongePluginDependency implements SpongePluginDependency {

    protected PluginContainer dependency;
    protected String dependencyName;
    protected boolean hooked;
    private PluginContainer handlingPlugin;

    /**
     * Constructs a new plugin dependency container for the plugin of the given name
     *
     * @param dependencyName Name of the plugin dependency to retrieve
     * @throws IllegalArgumentException if retrieval of the plugin with the given name is not of the type specified by
     *                                  {@code P}
     */
    public AbstractSpongePluginDependency(String dependencyName) {
        this(SpongeUtil.getGame().getPluginManager().getPlugin(dependencyName).orNull());
        this.dependencyName = dependencyName;
    }

    /**
     * Constructs a new plugin dependency container for the given plugin
     *
     * @param dependency Plugin dependency instance
     */
    public AbstractSpongePluginDependency(PluginContainer dependency) {
        this.handlingPlugin = SpongeUtil.getPluginContainer();
        confirmHook(dependency);
        prepareListeners();
    }

    private void prepareListeners() {
        SpongeUtil.getGame().getEventManager().register(handlingPlugin.getInstance(), new Object() {
            @Subscribe(order = Order.POST)
            public void onPostInitialization(PostInitializationEvent event) {
                // Final attempt at finding the plugin being depended upon
                if (dependency == null) {
                    confirmHook(SpongeUtil.getGame().getPluginManager().getPlugin(dependencyName).orNull());
                }
            }

            // TODO: not exactly a plugin disable event ;\
            @Subscribe(order = Order.POST)
            public void onServerStopping(ServerStoppingEvent event) {
                if (dependency != null) {
                    hooked = false;
                    onUnhook();
                    dependency = null;
                }
            }
        });
    }

    private void confirmHook(PluginContainer plugin) {
        this.dependency = plugin;
        if (this.dependency != null && SpongeUtil.getGame().getPluginManager().isLoaded(dependencyName)) {
            if (this.dependencyName == null) {
                this.dependencyName = dependency.getName();
            }
            this.hooked = true;
            this.onHook();
            SpongeLog.getConsole().sendMessage("Successfully hooked: " + this.dependencyName);
            return;
        }
        throw new IllegalArgumentException("Failed to provide dependency hook for " + dependencyName + " (Is it enabled and is the given type correct?)");
    }

    @Override
    public PluginContainer getHandlingPlugin() {
        return handlingPlugin;
    }

    @Override
    public PluginContainer getDependency() {
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