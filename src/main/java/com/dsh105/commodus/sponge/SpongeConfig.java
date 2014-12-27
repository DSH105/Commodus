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

import com.dsh105.commodus.configuration.AbstractConfig;
import com.dsh105.commodus.configuration.ConfigManager;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import org.spongepowered.api.util.config.ConfigFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpongeConfig extends AbstractConfig {

    // TODO: ಠ_ಠ
    private static final ConfigRenderOptions DEFAULT_OPTIONS = ConfigRenderOptions.defaults()
            .setComments(true)
            .setFormatted(true)
            .setJson(false)
            .setOriginComments(false);

    private ConfigFile configuration;

    public SpongeConfig(SpongeConfigManager manager, File file, String configContent, int totalComments) {
        this(manager, file, load(manager, file, configContent), totalComments);
    }

    public SpongeConfig(SpongeConfigManager manager, File file, ConfigFile configuration, int totalComments) {
        super(manager, file, totalComments);
        this.configuration = configuration;
    }

    private static ConfigFile load(ConfigManager manager, File configFile, String configContent) {
        ConfigFile spongeConfig = ConfigFile.parseFile(configFile);
        manager.save(configFile, configContent);
        return spongeConfig;

        // ... ._.
        /*try {
            ConfigFile configFile = new ConfigFile(file, ConfigFactory.parseReader(new InputStreamReader(configurationInput.getInput(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to initialise configuration.", e);
        }*/
    }

    public ConfigFile getConfiguration() {
        return configuration;
    }

    @Override
    public void save() {
        configuration.save(true);
        reload();
    }

    @Override
    public void reloadConfig() throws IOException {
        configuration = load(getManager(), getFile(), getManager().compile(this).getContent());
    }

    @Override
    public void set(String path, Object value) {
        // TODO: come again?
        //configuration = configuration.withValue(path, ?);
    }

    @Override
    public void set(String path, Object value, boolean save, String... comments) {
        if (!exists(path)) {
            // TODO: find a way to save comments
            /*for (String comment : comments) {
                set("CONFIG_COMMENT_" + this.totalComments++, " " + comment);
            }*/
        }

        set(path, value);
        if (save) {
            save();
        }
    }

    @Override
    public String saveToString() {
        return configuration.root().render(DEFAULT_OPTIONS);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        // TODO: we don't have to implement depth ourselves do we... :(
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, ConfigValue> entry : configuration.entrySet()) {
            keys.add(entry.getKey());
        }
        return Collections.unmodifiableSet(keys);
    }

    @Override
    public Object getObject(String path) {
        // TODO: all this just to retrieve an 'Object'?
        for (Map.Entry<String, ConfigValue> entry : configuration.entrySet()) {
            if (entry.getKey().equals(path)) {
                return entry.getValue().unwrapped();
            }
        }
        return null;
    }
}