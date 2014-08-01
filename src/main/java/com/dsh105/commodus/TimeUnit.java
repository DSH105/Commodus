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

/**
 * Converts between various time formats ranging from milliseconds to years
 */
public enum TimeUnit {

    /**
     * Represents milliseconds
     */
    MILLISECONDS(1),

    /**
     * Represents seconds
     */
    SECONDS(MILLISECONDS.multiplier * 1000),

    /**
     * Represents minutes
     */
    MINUTES(SECONDS.multiplier * 60),

    /**
     * Represents hours
     */
    HOURS(MINUTES.multiplier * 60),

    /**
     * Represents days
     */
    DAYS(HOURS.multiplier * 24),

    /**
     * Represents weeks
     */
    WEEKS(DAYS.multiplier * 7),

    /**
     * Represents years
     */
    YEARS(DAYS.multiplier * 52);

    private int multiplier;

    TimeUnit(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Converts a given amount of time in this unit to milliseconds
     *
     * @param time amount of time in terms of this unit
     * @return the time given converted to milliseconds
     */
    public long toMilliseconds(long time) {
        return time * multiplier;
    }

    /**
     * Converts a given amount of time in milliseconds into this unit
     *
     * @param milliseconds amount of time to convert
     * @return the time given converted from milliseconds to this unit
     */
    public long getTime(long milliseconds) {
        return milliseconds / multiplier;
    }
}