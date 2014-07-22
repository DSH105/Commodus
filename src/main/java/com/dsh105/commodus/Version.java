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

public class Version implements Comparable<Version> {

    private String version;
    private int[] numericVersion;

    public Version() {
        this(ServerUtil.getServerVersion());
    }

    public Version(String version) {
        this.version = version;
        this.numericVersion = getNumericVersion(version);
    }

    public Version(int numericVersion) {
        this.numericVersion = GeneralUtil.toDigits(numericVersion);

        StringBuilder builder = new StringBuilder();
        for (int i : this.numericVersion) {
            builder.append(i + builder.length() == 0 ? "." : "");
        }
        this.version = builder.toString();
    }

    public static int[] getNumericVersion(String serverVersion) {
        String[] versionParts = serverVersion.split("[.-]");
        int[] numericVersionParts = new int[versionParts.length];
        for (int i = 0; i < versionParts.length; i++) {
            try {
                numericVersionParts[i] = GeneralUtil.toInteger(versionParts[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid version: " + serverVersion);
            }
        }
        return numericVersionParts;
    }

    public String getVersion() {
        return version;
    }

    public int[] getNumericVersion() {
        return numericVersion;
    }

    public boolean isIdentical() {
        return isIdentical(ServerUtil.getVersion());
    }

    public boolean isCompatible() {
        return isCompatible(ServerUtil.getVersion());
    }

    public boolean isSupported() {
        return isSupported(ServerUtil.getVersion());
    }

    public boolean isIdentical(String version) {
        return isIdentical(new Version(version));
    }

    public boolean isCompatible(String minimumRequiredVersion) {
        return isCompatible(new Version(minimumRequiredVersion));
    }

    public boolean isSupported(String latestAllowedVersion) {
        return isSupported(new Version(latestAllowedVersion));
    }

    public boolean isIdentical(int version) {
        return isIdentical(new Version(version));
    }

    public boolean isCompatible(int minimumRequiredVersion) {
        return isCompatible(new Version(minimumRequiredVersion));
    }

    public boolean isSupported(int latestAllowedVersion) {
        return isSupported(new Version(latestAllowedVersion));
    }

    public boolean isIdentical(Version version) {
        return compareTo(version) == 0;
    }

    public boolean isCompatible(Version minimumRequiredVersion) {
        return compareTo(minimumRequiredVersion) >= 0;
    }

    public boolean isSupported(Version latestAllowedVersion) {
        return compareTo(latestAllowedVersion) <= 0;
    }

    public int compareTo(String minimumRequiredVersion) {
        return compareTo(new Version(minimumRequiredVersion));
    }

    public int compareTo(int minimumRequiredVersion) {
        return compareTo(new Version(minimumRequiredVersion));
    }

    @Override
    public int compareTo(Version minimumRequiredVersion) {
        int[] numericVersion = ServerUtil.getNumericServerVersion(getVersion());
        int[] numericVersionToCompare = minimumRequiredVersion.getNumericVersion();
        int maxLength = Math.max(numericVersion.length, numericVersionToCompare.length);
        for (int i = 0; i < maxLength; i++) {
            int versionPart = i < numericVersion.length ? numericVersion[i] : 0;
            int versionPartToCompare = i < numericVersionToCompare.length ? numericVersionToCompare[i] : 0;
            if (versionPart != versionPartToCompare) {
                // If the current version is more recent, returns > 1
                // else, returns < 1
                return versionPart - versionPartToCompare;
            }
        }
        return 0;
    }
}