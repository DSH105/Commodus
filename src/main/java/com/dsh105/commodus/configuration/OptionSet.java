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

package com.dsh105.commodus.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class OptionSet {

    // TODO: docs, tests

    private Config config;

    public OptionSet(Config config) {
        this.config = config;
        setDefaults();
        this.config.save();
    }

    public abstract void setDefaults();

    protected static OptionSet getInstance() {
        throw new UnsupportedOperationException("Sub class of OptionSet must implement getInstance()");
    }

    public Config getConfig() {
        return config;
    }

    public <V> void setDefault(Option<V> option, String... replacements) {
        option.setValue(option.getDefaultValue(), replacements);
    }

    public <V> void set(Option<V> option, V value, String... replacements) {
        option.setValue(value, replacements);
    }

    public <V> V get(Option<V> option, String... replacements) {
        return option.getValue(replacements);
    }

    public boolean isLocked(Option<?> option, String... replacements) {
        return option.isLocked(replacements);
    }

    public <V> V getLockedValue(Option<V> option, String... replacements) {
        return option.getLockedValue(replacements);
    }

    public <V> void lock(Option<V> option, V value, String... replacements) {
        option.lock(value, replacements);
    }

    public void unlock(Option<?> option, String... replacements) {
        option.unlock(replacements);
    }

    public List<Option> getOptions() throws IllegalAccessException {
        return getOptions(Option.class);
    }

    public <T extends Option> List<T> getOptions(Class<T> optionType) throws IllegalAccessException {
        List<T> options = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (optionType.isAssignableFrom(field.getType())) {
                if (Modifier.isStatic(Modifier.fieldModifiers())) {
                    options.add((T) field.get(null));
                }
            }
        }
        return options;
    }

    public static <V> Option<V> option(String path, V defaultValue, String... comments) {
        return new Option<>(getInstance().getConfig(), path, defaultValue, comments);
    }

    public static <V> Option<V> option(Class<V> optionType, String path, String... comments) {
        return new Option<>(getInstance().getConfig(), path, comments);
    }

    public static Option<String> optionString(String path, String defaultValue, String... comments) {
        return option(path, defaultValue, comments);
    }

    public static Option<String> optionString(String path, String[] comments) {
        return option(String.class, path, comments);
    }

    public static Option<String> optionString(String path) {
        return option(String.class, path);
    }
}