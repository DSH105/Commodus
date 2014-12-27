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

import java.util.Collection;

/**
 * Basic independent utilities for affirming certain cases
 */
public class Affirm {

    // TODO: docs?

    private static volatile AffirmationCallable errorCallable;

    private Affirm() {

    }

    public static AffirmationCallable getErrorCallable() {
        return errorCallable;
    }

    public static void setErrorCallable(AffirmationCallable errorCallable) {
        Affirm.errorCallable = errorCallable;
    }

    public static <T> T notNull(T object) {
        return notNull(object, null);
    }

    public static <T> T notNull(T object, String message) {
        if (object == null) {
            throwException(new NullPointerException(message));
        }
        return object;
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, null);
    }

    public static <T> T[] notEmpty(T[] array, String message) {
        if (array == null || array.length == 0) {
            throwException(new IllegalArgumentException(message));
        }
        return array;
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection) {
        return notEmpty(collection, null);
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection, String message) {
        if (collection == null || collection.size() == 0) {
            throwException(new IllegalArgumentException(message));
        }
        return collection;
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, null);
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throwException(new IllegalArgumentException(message));
        }
    }

    public static void checkInstanceOf(Class<?> type, Object instance, boolean allowNull) {
        Affirm.notNull(type);
        if (!allowNull) {
            Affirm.notNull(instance);
        }
        if (!type.isAssignableFrom(instance.getClass())) {
            throwException(new IllegalStateException(instance.getClass().getCanonicalName() + " must be a subclass of " + type.getCanonicalName()));
        }
    }

    public static void checkInstanceOf(Class<?> type, Object instance) {
        checkInstanceOf(type, instance, false);
    }

    private static void throwException(RuntimeException defThrowable) {
        if (errorCallable != null) {
            try {
                errorCallable.call(defThrowable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw defThrowable;
        }
    }
}