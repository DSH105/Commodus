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
import com.dsh105.commodus.exceptions.RemapperUnavailableException;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Accesses Cauldron/MCPC remapping for classes, fields and methods
 * <p>
 * Credits go to Protocol Lib for most of the stuff here:
 * https://github.com/aadnk/ProtocolLib/blob/master/ProtocolLib/src/main/java/com/comphenix/protocol/utility/RemappedClassSource.java
 * <p>
 * And more credits to CaptainBern's magical reflection library for the field and method stuff:
 * https://github.com/CaptainBern/Reflection/blob/master/Minecraft-Reflection/src/main/java/com/captainbern/minecraft/reflection/providers/remapper/RemappedReflectionProvider.java
 * <p>
 * The source of all this wonderful remapping stuff:
 * https://github.com/MinecraftPortCentral/Cauldron/blob/master/patches/org/bukkit/plugin/java/PluginClassLoader.java.patch
 */
public class RemappedClassLoader {

    private ClassLoader loader;
    private Object classRemapper;

    private Method mapType;
    private Method mapField;
    private Method mapMethod;

    // Remapped classes, fields and methods
    private Map<String, String> remappedClasses;
    private Map<String, String> remappedFields;
    private Map<String, String> remappedMethods;

    public RemappedClassLoader() {
        this(RemappedClassLoader.class.getClassLoader());
    }

    public RemappedClassLoader(ClassLoader loader) {
        this.loader = loader;
        initialise();
    }

    private void initialise() {
        try {
            if (!ServerUtil.isCauldron()) {
                throw new RemapperUnavailableException(RemapperUnavailableException.Reason.CAULDRON_NOT_PRESET);
            }
        } catch (Exception e) {
            if (e instanceof RemapperUnavailableException) {
                throw e;
            }
            throw new RemapperUnavailableException("Failed to access Cauldron remapper", e);
        }

        classRemapper = Reflection.getFieldValue(loader, "remapper");
        if (classRemapper == null) {
            throw new RemapperUnavailableException(RemapperUnavailableException.Reason.REMAPPER_DISABLED);
        }

        // https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarRemapper.java#L87
        mapType = Reflection.getMethod(classRemapper.getClass(), "map", String.class);

        // https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarRemapper.java#L157
        mapField = Reflection.getMethod(classRemapper.getClass(), "mapFieldName", String.class, String.class, String.class, int.class);

        //https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarRemapper.java#L163
        mapMethod = Reflection.getMethod(classRemapper.getClass(), "mapMethodName", String.class, String.class, String.class, int.class);

        // https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarRemapper.java#L50
        Object jarMapping = Reflection.getFieldValue(classRemapper, "jarMapping");

        // https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarMapping.java#L48-L50
        remappedClasses = (Map<String, String>) Reflection.getFieldValue(jarMapping, "classes");
        remappedFields = (Map<String, String>) Reflection.getFieldValue(jarMapping, "fields");
        remappedMethods = (Map<String, String>) Reflection.getFieldValue(jarMapping, "methods");
    }

    public Class<?> loadClass(String canonicalName) throws ClassNotFoundException {
        String remapped = getRemappedClassName(canonicalName);
        try {
            return loader.loadClass(remapped);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Failed to find class: " + canonicalName + "(remapped: " + remapped + ")");
        }
    }

    public String getRemappedFieldName(Class<?> clazz, String fieldName) {
        return (String) Reflection.invoke(mapField, classRemapper, getUnmappedClassName(clazz), fieldName, null, -1);
    }

    public String getRemappedMethodName(Class<?> clazz, String methodName, Class<?>... args) {
        String path = getUnmappedClassName(clazz) + "/" + methodName + " ";
        for (Map.Entry<String, String> entry : remappedMethods.entrySet()) {
            if (entry.getKey().startsWith(path)) {
                try {
                    clazz.getDeclaredMethod(entry.getValue(), args);
                    // This looks like the one
                    return entry.getValue();
                } catch (NoSuchMethodException ignored) {
                    // Nope...
                }
            }
        }
        return methodName;
    }

    private String getRemappedClassName(String canonicalName) {
        try {
            return ((String) Reflection.invoke(mapType, classRemapper, canonicalName.replace('.', '/'))).replace('/', '.');
        } catch (Exception e) {
            throw new RuntimeException("Failed to remap class name: " + canonicalName);
        }
    }

    private String getUnmappedClassName(Class<?> clazz) {
        String remappedCanonicalName = clazz.getCanonicalName().replace('.', '/');
        for (Map.Entry<String, String> entry : remappedClasses.entrySet()) {
            if (entry.getValue().equals(remappedCanonicalName)) {
                return entry.getKey();
            }
        }
        return remappedCanonicalName;
    }
}