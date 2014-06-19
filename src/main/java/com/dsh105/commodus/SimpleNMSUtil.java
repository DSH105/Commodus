package com.dsh105.commodus;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.Reflection;
import org.bukkit.entity.Entity;

public class SimpleNMSUtil {

    public static Object getEntityHandle(Entity entity) {
        return new Reflection().reflect(MinecraftReflection.getCraftEntityClass()).getSafeMethod("getHandle").getAccessor().invoke(entity);
    }
}