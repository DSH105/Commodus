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

package com.dsh105.commodus.reflection;

import com.dsh105.commodus.ServerUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reflection {

    private static Map<String, Class> LOADED_NMS_CLASSES = new HashMap<>();
    private static Map<String, Class> LOADED_OBC_CLASSES = new HashMap<>();
    private static Map<Class, Map<String, Map<Class<?>[], Method>>> LOADED_METHODS = new HashMap<>();
    private static Map<Class, Map<String, Field>> LOADED_FIELDS = new HashMap<>();

    public static Class<?> getNMSClass(String className) {
        // TODO: This needs MCPC+/Cauldron support
        if (LOADED_NMS_CLASSES.containsKey(className)) {
            return LOADED_NMS_CLASSES.get(className);
        }
        try {
            Class nmsClass = Class.forName("net.minecraft.server." + ServerUtil.getMCPackage() + "." + className);
            if (nmsClass != null) {
                LOADED_NMS_CLASSES.put(className, nmsClass);
            }
            return nmsClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getOBCClass(String className) {
        if (LOADED_OBC_CLASSES.containsKey(className)) {
            return LOADED_OBC_CLASSES.get(className);
        }
        Class obcClass = null;
        try {
            obcClass = Class.forName("org.bukkit.craftbukkit." + ServerUtil.getMCPackage() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LOADED_OBC_CLASSES.put(className, obcClass);
        return obcClass;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        Class<?>[] params = parameters == null ? new Class<?>[0] : parameters;
        Map<String, Map<Class<?>[], Method>> loadedMethodNames = getLoadedMethods(clazz);
        Map<Class<?>[], Method> loadedMethodParams = getLoadedMethods(clazz, methodName);

        Method method = getLoadedMethod(clazz, methodName, params);
        if (method == null) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameters);
                method.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        loadedMethodParams.put(params, method);
        loadedMethodNames.put(methodName, loadedMethodParams);
        LOADED_METHODS.put(clazz, loadedMethodNames);
        return method;
    }

    public static Map<String, Map<Class<?>[], Method>> getLoadedMethods(Class<?> clazz) {
        Map<String, Map<Class<?>[], Method>> loadedMethods = LOADED_METHODS.get(clazz);
        if (loadedMethods == null) {
            loadedMethods = new HashMap<>();
        }
        return loadedMethods;
    }

    public static Map<Class<?>[], Method> getLoadedMethods(Class<?> clazz, String methodName) {
        Map<Class<?>[], Method> loadedMethods = getLoadedMethods(clazz).get(methodName);
        if (loadedMethods == null) {
            loadedMethods = new HashMap<>();
        }
        return loadedMethods;
    }

    public static Method getLoadedMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        return getLoadedMethods(clazz, methodName).get(parameters);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Map<String, Field> loadedFields = getLoadedFields(clazz);
        if (loadedFields.containsKey(fieldName)) {
            return loadedFields.get(fieldName);
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        loadedFields.put(fieldName, field);
        LOADED_FIELDS.put(clazz, loadedFields);
        return field;
    }

    public static Object getFieldValue(Class<?> clazz, Object instance, String fieldName) {
        return getFieldValue(getField(clazz, fieldName), instance);
    }

    public static Object getFieldValue(Object instance, String fieldName) {
        return getFieldValue(getField(instance.getClass(), fieldName), instance);
    }

    public static Object getFieldValue(Field field, Object instance) {
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Field> getLoadedFields(Class<?> clazz) {
        Map<String, Field> loadedFields = LOADED_FIELDS.get(clazz);
        if (loadedFields == null) {
            loadedFields = new HashMap<>();
        }
        return loadedFields;
    }

    public static Object invoke(Method method, Object instance, Object... parameters) {
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(instance, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeStatic(Method method, Object... parameters) {
        return invoke(method, null, parameters);
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameters) {
        try {
            return clazz.getConstructor(parameters);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class[] convert(Object[] parameters) {
        Class<?>[] classParams = null;
        if (parameters != null) {
            if (parameters.length <= 0) {
                classParams = new Class<?>[0];
            } else {
                List<Class<?>> params = new ArrayList<>();
                for (Object param : parameters) {
                    params.add(param.getClass());
                    classParams = params.toArray(new Class[0]);
                }
            }
        }
        return classParams;
    }
}