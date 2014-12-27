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
import com.dsh105.commodus.Version;

// TODO: docs
// TODO: perhaps a system to detect this? ('expected' version formats?)
public class BukkitVersion extends Version {

    public BukkitVersion(String version) {
        super(prepare(version));
    }

    public BukkitVersion(int numericVersion) {
        super(numericVersion);
    }

    private static String prepare(String version) {
        String[] section = version.split("-");
        String[] primaryElements = section[0].split("\\.");
        String[] secondaryElements = section[0].split("\\.");
        int[] numericVersion = new int[3];

        if (primaryElements.length == 0) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }

        for (int i = 0; i < numericVersion.length; i++) {
            numericVersion[i] = i < primaryElements.length ? GeneralUtil.toInteger(primaryElements[i]) : 0;
        }

        return String.format("%s.%s.%s-R%s.%s", numericVersion[0], numericVersion[1], numericVersion[2], secondaryElements[0], secondaryElements[1]);
    }
}