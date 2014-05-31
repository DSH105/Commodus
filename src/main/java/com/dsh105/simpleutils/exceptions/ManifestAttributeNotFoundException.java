/*
 * This file is part of SimpleUtils.
 *
 * SimpleUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SimpleUtils.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.simpleutils.exceptions;

public class ManifestAttributeNotFoundException extends RuntimeException {

    public ManifestAttributeNotFoundException(String s) {
        super(s);
    }

    public ManifestAttributeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ManifestAttributeNotFoundException(Throwable throwable) {
        super(throwable);
    }
}