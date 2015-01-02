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

import com.dsh105.commodus.reflection.Reflection;

public class AchievementContainer extends Container<AchievementContainer> {

    // TODO: docs
    // TODO: sponge

    private Object achievement;
    private String achievementName;

    private AchievementContainer(Object achievement, String achievementName) {
        this.achievement = achievement;
        this.achievementName = achievementName;
    }

    public static AchievementContainer from(org.bukkit.Achievement achievement) {
        Object nmsAchievement = Reflection.invokeStatic(Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSAchievement", org.bukkit.Achievement.class), achievement);
        return new AchievementContainer(achievement, (String) Reflection.getFieldValue(Reflection.getNMSClass("Achievement"), nmsAchievement, "name"));
    }

    public String getAchievementName() {
        return achievementName;
    }

    public Object getAchievement() {
        return achievement;
    }

    public org.bukkit.Achievement asBukkit() {
        if (achievement instanceof org.bukkit.Achievement) {
            return (org.bukkit.Achievement) achievement;
        }
        throw new IllegalStateException("Achievement contained is not a Bukkit Achievement.");
    }
}