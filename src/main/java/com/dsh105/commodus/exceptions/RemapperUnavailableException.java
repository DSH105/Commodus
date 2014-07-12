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

package com.dsh105.commodus.exceptions;

public class RemapperUnavailableException extends RuntimeException {

    public RemapperUnavailableException(Reason reason) {
        super(reason.getMessage());
    }

    public RemapperUnavailableException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public enum Reason {
        CAULDRON_NOT_PRESET("Cauldron/MCPC+ is not present!"),
        REMAPPER_DISABLED("Cauldron/MCPC+ detected, but the remapper is disabled!");

        private String message;

        Reason(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}