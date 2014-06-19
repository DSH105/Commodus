package com.dsh105.commodus;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtil {

    public static int MC_VERSION_NUMERIC = Integer.valueOf(getServerVersion().replaceAll("[^0-9]", ""));
    public static int BUKKIT_VERSION_NUMERIC = Integer.valueOf(getBukkitVersion().replaceAll("[^0-9]", ""));

    public static String getServerVersion() {
        return MinecraftReflection.getVersionTag();
    }

    // Thanks ProtocolLib <3
    public static String getBukkitVersion() {
        Pattern versionPattern = Pattern.compile(".*\\(.*MC.\\s*([a-zA-z0-9\\-\\.]+)\\s*\\)");
        Matcher version = versionPattern.matcher(Bukkit.getServer().getVersion());

        if (version.matches() && version.group(1) != null) {
            return version.group(1);
        } else {
            return "";
        }
    }
}