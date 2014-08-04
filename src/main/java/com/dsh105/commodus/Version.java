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
 * Represents a server version that can be utilised as a comparison
 */
public class Version implements Comparable<Version> {

    private String version;
    private int[] numericVersion;

    /**
     * Constructs a new Version from the current server version running
     */
    public Version() {
        this(ServerUtil.getServerVersion());
    }

    /**
     * Constructs a new Version from the given server version
     *
     * @param version server version e.g. 1.7.10-R0.1
     */
    public Version(String version) {
        this.version = version;
        this.numericVersion = getNumericVersion(version);
    }

    /**
     * Constructs a new Version from the given numeric server version
     * <p>
     * <strong>Not recommended for public API consumption</strong>
     *
     * @param numericVersion numeric server version e.g. 1.7.10-R0.1 would be 171001
     */
    public Version(int numericVersion) {
        this.numericVersion = GeneralUtil.toDigits(numericVersion);

        StringBuilder builder = new StringBuilder();
        for (int i : this.numericVersion) {
            builder.append(i + builder.length() == 0 ? "." : "");
        }
        this.version = builder.toString();
    }

    /**
     * Returns an array of integers that represents the given server version
     *
     * @param serverVersion version to convert to a numeric array
     * @return a numeric array representing the {@code serverVersion} given
     */
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

    /**
     * Gets this version in string format
     *
     * @return the version represented by this object as a string
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns an array of integers that represents this server version
     *
     * @return a numeric array representing this version instance
     */
    public int[] getNumericVersion() {
        return numericVersion;
    }

    /**
     * Returns whether or not this version is identical to the currently running server version
     * <p>
     * For example: 1.7.10 matches 1.7.10, but not 1.7.9 or 1.7.8
     *
     * @return true if the two versions are identical
     */
    public boolean isIdentical() {
        return isIdentical(ServerUtil.getVersion());
    }

    /**
     * Returns whether or not the currently running server version is compatible with this version
     * <p>
     * Makes a comparison to see if the version currently running on the server is more recent (compatible) or
     * identical
     * to than this version. For example, if this version is 1.7.9, a server
     * running 1.7.10 or 1.7.9 will be considered compatible, whereas a server running 1.7.8 will not
     *
     * @return true if the currently running server version is compatible with this version
     */
    public boolean isCompatible() {
        return isCompatible(ServerUtil.getVersion());
    }

    /**
     * Returns whether or not this version supports the currently running server version
     * <p>
     * Makes a comparison to see if the version currently running on the server is earlier (supported) or identical
     * to than this version. For example, if this version is 1.7.9, a server
     * running 1.7.8 or 1.7.6 will be considered supported, whereas a server running 1.7.10 will not
     *
     * @return true if the currently running server version is supported according to this version
     */
    public boolean isSupported() {
        return isSupported(ServerUtil.getVersion());
    }

    /**
     * Returns whether or not this version is identical to the given version
     * <p>
     * For example: 1.7.10 matches 1.7.10, but not 1.7.9 or 1.7.8
     *
     * @param version server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the two versions are identical
     */
    public boolean isIdentical(String version) {
        return isIdentical(new Version(version));
    }

    /**
     * Returns whether or not the given version is compatible with this version
     * <p>
     * Makes a comparison to see if the given version is more recent (compatible) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.10 or 1.7.9 will be considered
     * compatible, whereas 1.7.8 will not
     *
     * @param minimumRequiredVersion server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the {@code minimumRequiredVersion} is compatible with this version
     */
    public boolean isCompatible(String minimumRequiredVersion) {
        return isCompatible(new Version(minimumRequiredVersion));
    }

    /**
     * Returns whether or not this version supports the given version
     * <p>
     * Makes a comparison to see if the version given is earlier (supported) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.8 or 1.7.6 will be considered
     * supported, whereas 1.7.10 will not
     *
     * @return true if {@code latestAllowedVersion} is supported by this version
     */
    public boolean isSupported(String latestAllowedVersion) {
        return isSupported(new Version(latestAllowedVersion));
    }

    /**
     * Returns whether or not this version is identical to the given version
     * <p>
     * For example: 1.7.10 matches 1.7.10, but not 1.7.9 or 1.7.8
     *
     * @param version server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the two versions are identical
     */
    public boolean isIdentical(int version) {
        return isIdentical(new Version(version));
    }

    /**
     * Returns whether or not the given version is compatible with this version
     * <p>
     * Makes a comparison to see if the given version is more recent (compatible) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.10 or 1.7.9 will be considered
     * compatible, whereas 1.7.8 will not
     *
     * @param minimumRequiredVersion server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the {@code minimumRequiredVersion} is compatible with this version
     */
    public boolean isCompatible(int minimumRequiredVersion) {
        return isCompatible(new Version(minimumRequiredVersion));
    }

    /**
     * Returns whether or not this version supports the given version
     * <p>
     * Makes a comparison to see if the version given is earlier (supported) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.8 or 1.7.6 will be considered
     * supported, whereas 1.7.10 will not
     *
     * @return true if {@code latestAllowedVersion} is supported by this version
     */
    public boolean isSupported(int latestAllowedVersion) {
        return isSupported(new Version(latestAllowedVersion));
    }

    /**
     * Returns whether or not this version is identical to the given version
     * <p>
     * For example: 1.7.10 matches 1.7.10, but not 1.7.9 or 1.7.8
     *
     * @param version server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the two versions are identical
     */
    public boolean isIdentical(Version version) {
        return compareTo(version) == 0;
    }

    /**
     * Returns whether or not the given version is compatible with this version
     * <p>
     * Makes a comparison to see if the given version is more recent (compatible) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.10 or 1.7.9 will be considered
     * compatible, whereas 1.7.8 will not
     *
     * @param minimumRequiredVersion server version to make a comparison against e.g. 1.7.10-R0.1
     * @return true if the {@code minimumRequiredVersion} is compatible with this version
     */
    public boolean isCompatible(Version minimumRequiredVersion) {
        return compareTo(minimumRequiredVersion) >= 0;
    }

    /**
     * Returns whether or not this version supports the given version
     * <p>
     * Makes a comparison to see if the version given is earlier (supported) or identical
     * to than this version. For example, if this version is 1.7.9, a version of 1.7.8 or 1.7.6 will be considered
     * supported, whereas 1.7.10 will not
     *
     * @return true if {@code latestAllowedVersion} is supported by this version
     */
    public boolean isSupported(Version latestAllowedVersion) {
        return compareTo(latestAllowedVersion) <= 0;
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