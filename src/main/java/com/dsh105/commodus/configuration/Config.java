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

import com.dsh105.commodus.Transformer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Config {

    // TODO: docs

    ConfigManager<?> getManager();

    File getFile();

    boolean isLocked(String path);

    Object getLockedValue(String path);

    void lock(String path, Object value);

    void unlock(String path);

    Map<String, Object> getLockedValues();

    void setHeader(String... header);

    void save();

    String saveToString();

    void reload();

    void set(String path, Object value, String... comments);

    void set(String path, Object value, boolean save, String... comments);

    boolean exists(String path);

    Set<String> getKeys(boolean deep);

    Object get(String path);

    <T> T get(String path, T defaultValue);

    Object getObject(String path);

    String getString(String path);

    int getInt(String path);

    boolean getBoolean(String path);

    double getDouble(String path);

    float getFloat(String path);

    long getLong(String path);

    List<?> getList(String path);

    <T> List<T> getList(String path, Transformer<Object, T> transformer);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    List<Boolean> getBooleanList(String path);

    List<Double> getDoubleList(String path);

    List<Float> getFloatList(String path);

    List<Long> getLongList(String path);

    List<Byte> getByteList(String path);

    List<Character> getCharacterList(String path);

    List<Short> getShortList(String path);

    List<Map<?, ?>> getMapList(String path);
}