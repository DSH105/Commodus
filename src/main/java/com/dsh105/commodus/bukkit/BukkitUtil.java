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

package com.dsh105.commodus.bukkit;

import com.dsh105.commodus.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class BukkitUtil {

    /**
     * Parses a location from a set of String arguments
     * <p/>
     * Arguments must be in the following order:
     * <ul>
     *     <li>World name</li>
     *     <li>X coordinate</li>
     *     <li>Y coordinate</li>
     *     <li>Z coordinate</li
     *     <li><b>Optional: </b>Yaw</li>
     *     <li><b>Optional: </b>Pitch</li>
     * </ul>
     *
     * @param startIndex Index to start parsing location from
     * @param args       String arguments to parse location from
     * @return A Location built from the given args, or null if it could not be parsed
     * @throws java.lang.IllegalStateException if the world at {@code args[startIndex]} does not exist
     */
    public static Location readLocation(int startIndex, String... args) {
        return GeneralUtil.readLocation(startIndex, args).toBukkit();
    }
}