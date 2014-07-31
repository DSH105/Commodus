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

package com.dsh105.commodus;

public enum TimeUnit {

    MILLISECONDS(1),
    SECONDS(MILLISECONDS.multiplier * 1000),
    MINUTES(SECONDS.multiplier * 60),
    HOURS(MINUTES.multiplier * 60),
    DAYS(HOURS.multiplier * 24),
    WEEKS(DAYS.multiplier * 7);

    private int multiplier;

    TimeUnit(int multiplier) {
        this.multiplier = multiplier;
    }

    public long toMilliseconds(long time) {
        return time * multiplier;
    }

    public long getTime(long milliseconds) {
        return milliseconds / multiplier;
    }
}