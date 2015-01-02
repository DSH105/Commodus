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
import com.dsh105.commodus.reflection.Reflection;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class StatisticContainer extends Container<StatisticContainer> {

    // TODO: docs
    // TODO: sponge

    private Object statistic;
    private String statisticName;

    private StatisticContainer(Object statistic, String statisticName) {
        this.statistic = statistic;
        this.statisticName = statisticName;
    }

    public static StatisticContainer from(Statistic statistic) {
        Affirm.isTrue(statistic.getType() == Statistic.Type.UNTYPED, "That statistic requires an additional " + statistic.getType() + " parameter!");
        Object statisticHandle = Reflection.invokeStatic(Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSStatistic", Statistic.class), statistic);
        return new StatisticContainer(statistic, (String) Reflection.getFieldValue(Reflection.getNMSClass("Statistic"), statisticHandle, "name"));
    }

    public static StatisticContainer from(Statistic statistic, Material material) {
        if (statistic.getType() == Statistic.Type.UNTYPED) {
            return from(statistic);
        }
        Affirm.isTrue(!((statistic.getType() == Statistic.Type.BLOCK && statistic.isBlock()) || statistic.getType() == Statistic.Type.ENTITY), "Wrong parameter type for that statistic - needs " + statistic.getType() + "!");
        Object statisticHandle = Reflection.invokeStatic(Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getMaterialStatistic", Statistic.class, Material.class), statistic, material);
        return new StatisticContainer(statistic, (String) Reflection.getFieldValue(Reflection.getNMSClass("Statistic"), statisticHandle, "name"));
    }

    public static StatisticContainer from(Statistic statistic, Entity entity) {
        if (statistic.getType() == Statistic.Type.UNTYPED) {
            return from(statistic);
        }
        Affirm.isTrue(statistic.getType() != Statistic.Type.ENTITY, "Wrong parameter type for that statistic - needs " + statistic.getType() + "!");
        Object statisticHandle = Reflection.invokeStatic(Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getEntityStatistic", Statistic.class, EntityType.class), statistic, entity);
        return new StatisticContainer(statistic, (String) Reflection.getFieldValue(Reflection.getNMSClass("Statistic"), statisticHandle, "name"));
    }

    public String getStatisticName() {
        return statisticName;
    }

    public Object getStatistic() {
        return statistic;
    }

    public org.bukkit.Statistic asBukkit() {
        if (statistic instanceof org.bukkit.Statistic) {
            return (org.bukkit.Statistic) statistic;
        }
        throw new IllegalStateException("Statistic contained is not a Bukkit Statstic.");
    }
}