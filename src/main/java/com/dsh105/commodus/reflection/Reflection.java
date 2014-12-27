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

import com.dsh105.commodus.bukkit.BukkitServerUtil;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple reflection utilities, built from a Minecraft-oriented perspective
 * <p/>
 * Supports MCPC+/Cauldron remappings, through the {@link com.dsh105.commodus.reflection.RemappedClassLoader}
 */
public class Reflection {

    private static RemappedClassLoader REMAPPED_CLASS_LOADER;
    private static boolean INITIALISED;

    private static Map<String, Class> LOADED_CLASSES = new HashMap<>();
    private static Map<Class, Map<String, Map<Class<?>[], Method>>> LOADED_METHODS = new HashMap<>();
    private static Map<Class, Map<String, Field>> LOADED_FIELDS = new HashMap<>();

    private static RemappedClassLoader getRemappedClassLoader() {
        if (!INITIALISED && REMAPPED_CLASS_LOADER == null) {
            INITIALISED = true;
            try {
                REMAPPED_CLASS_LOADER = new RemappedClassLoader();
            } catch (RemapperUnavailableException ignored) {
                // Cauldron probably isn't enabled
                REMAPPED_CLASS_LOADER = null;
            }
        }
        return REMAPPED_CLASS_LOADER;
    }

    public static Class<?> getClass(String className) {
        if (LOADED_CLASSES.containsKey(className)) {
            return LOADED_CLASSES.get(className);
        }
        try {
            Class clazz = Class.forName(className);
            if (clazz != null) {
                LOADED_CLASSES.put(className, clazz);
            }
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + BukkitServerUtil.getVersionTag() + "." + className;
        if (getRemappedClassLoader() != null) {
            try {
                return getRemappedClassLoader().loadClass(fullName);
            } catch (ClassNotFoundException ignored) {
            }
        }
        return getClass(fullName);
    }

    public static Class<?> getOBCClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + BukkitServerUtil.getVersionTag() + "." + className;
        if (getRemappedClassLoader() != null) {
            try {
                return getRemappedClassLoader().loadClass(fullName);
            } catch (ClassNotFoundException ignored) {
            }
        }
        return getClass(fullName);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        if (getRemappedClassLoader() != null) {
            methodName = getRemappedClassLoader().getRemappedMethodName(clazz, methodName, parameters);
        }

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
        if (getRemappedClassLoader() != null) {
            fieldName = getRemappedClassLoader().getRemappedFieldName(clazz, fieldName);
        }

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

    /**
     * Get the underlying class for a type, or null if the type is a variable type.
     * <p/>
     * Source: http://www.artima.com/weblogs/viewpost.jsp?thread=208860
     *
     * @param type the type
     * @return the underlying class
     */
    public static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
            return null;
        }
        return null;
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     * <p/>
     * Source: http://www.artima.com/weblogs/viewpost.jsp?thread=208860
     *
     * @param baseClass  the base class
     * @param childClass the child class
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }

        for (Class<?> c : typeArgumentsAsClasses) {
            if (c != null) {
                System.out.println(c.getCanonicalName());
            }
        }
        return typeArgumentsAsClasses;
    }
}