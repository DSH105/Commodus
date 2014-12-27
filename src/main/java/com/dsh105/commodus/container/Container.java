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

package com.dsh105.commodus.container;

import com.dsh105.commodus.Affirm;

import java.util.HashMap;
import java.util.Map;

/**
 * A container provides quick, simple methods for storing type-based contextual values.
 *
 * @param <C> Type of container
 */
public class Container<C extends Container<C>> {

    private Map<Class<?>, Object> context = new HashMap<>();
    private Map<String, Object> keyBasedContext = new HashMap<>();

    /**
     * Stores the given object as a contextual value under the provided type.
     *
     * @param type   Type of object to be stored
     * @param object Object to be stored
     * @param <T>    Type of object to be stored
     * @return This container
     */
    public <T> C put(Class<T> type, T object) {
        context.put(type, object);
        return (C) this;
    }

    /**
     * Retrieves a contextual value stored under the given type.
     *
     * @param type Type that the object was mapped under
     * @param <T>  Type of object to be stored
     * @return Contextual value mapped to the given {@code type}
     */
    public <T> T get(Class<T> type) {
        return (T) context.get(type);
    }

    /**
     * Clears any stored contextual values.
     *
     * @return This container
     */
    public C clear() {
        context.clear();
        keyBasedContext.clear();
        return (C) this;
    }

    /**
     * Stores the given object as a contextual value under the provided key.
     *
     * @param key    Key to store the object under
     * @param object Object to be stored
     * @return This container
     */
    public C put(String key, Object object) {
        keyBasedContext.put(key, object);
        return (C) this;
    }

    /**
     * Retrieves a contextual value stored under the given key.
     *
     * @param key   Key that the object was mapped under
     * @return Contextual value mapped to the given {@code key}
     */
    public Object get(String key) {
        return keyBasedContext.get(key);
    }

    /**
     * Retrieves a contextual value stored under the given key.
     *
     * @param key   Key that the object was mapped under
     * @return Contextual value mapped to the given {@code key}
     * @throws java.lang.IllegalStateException if the retrieved value is not of the given type
     */
    public <T> T get(Class<T> type, String key) {
        Object value = keyBasedContext.get(key);
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Retrieved value (type=" + value.getClass().getCanonicalName() + ") is not of the given type (" + type.getCanonicalName() + ").");
        }
        return (T) value;
    }
}