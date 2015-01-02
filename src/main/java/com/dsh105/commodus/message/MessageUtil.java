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

package com.dsh105.commodus.message;

import com.dsh105.commodus.ServerBrand;
import com.dsh105.commodus.ServerUtil;
import com.dsh105.commodus.reflection.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class MessageUtil {

    public static final char COLOR_CHAR = '\u00A7';

    protected static Class<?> CHAT_PACKET_CLASS;
    protected static Method CHAT_FROM_JSON;

    static {
        // Retrieve the chat packet (for NMS in Bukkit)
        if (ServerUtil.getServerBrand().getCapsule() == ServerBrand.Capsule.BUKKIT) {
            if (ServerUtil.getPlatformVersion().isCompatible("1.7")) {
                for (Method method : Reflection.getNMSClass("ChatSerializer").getDeclaredMethods()) {
                    if (method.getReturnType().equals(Reflection.getNMSClass("IChatBaseComponent")) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(String.class)) {
                        CHAT_FROM_JSON = method;
                        break;
                    }
                }

                ArrayList<Method> packetMethods = new ArrayList<>();
                ArrayList<Field> packetFields = new ArrayList<>();
                for (Method method : Reflection.getNMSClass("EnumProtocol").getDeclaredMethods()) {
                    if (Map.class.isAssignableFrom(method.getReturnType()) && method.getParameterTypes().length == 0) {
                        method.setAccessible(true);
                        packetMethods.add(method);
                    }
                }

                Map packetPlayMap;

                if (!packetMethods.isEmpty()) {
                    packetPlayMap = (Map) Reflection.invoke(packetMethods.get(0), Reflection.getNMSClass("EnumProtocol").getEnumConstants()[1]);
                } else {
                    for (Field field : Reflection.getNMSClass("EnumProtocol").getDeclaredFields()) {
                        if (Map.class.isAssignableFrom(field.getType())) {
                            field.setAccessible(true);
                            packetFields.add(field);
                        }
                    }
                    packetPlayMap = (Map) Reflection.getFieldValue(packetFields.get(1), Reflection.getNMSClass("EnumProtocol").getEnumConstants()[1]);
                }

                if (Reflection.getNMSClass("EnumProtocolDirection") != null) {
                    packetPlayMap = (Map) packetPlayMap.get(Reflection.getNMSClass("EnumProtocolDirection").getEnumConstants()[1]);
                }
                CHAT_PACKET_CLASS = (Class<?>) packetPlayMap.get(0x02);

                try {
                    CHAT_PACKET_CLASS.getConstructor(Reflection.getNMSClass("IChatBaseComponent"));
                } catch (NoSuchMethodException e) {
                    // This is more of a backup
                    CHAT_PACKET_CLASS = Reflection.getNMSClass("PacketPlayOutChat");
                }
            }
        }

    }
}