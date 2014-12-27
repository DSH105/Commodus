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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Utilities for managing the Manifest file in a compiled JAR at runtime
 */
public class ManifestUtil {

    private ManifestUtil() {
    }

    /**
     * Returns the Manifest file at a given path
     *
     * @param path Path to search for the file
     * @return The manifest file at the given file path
     * @throws java.io.IOException if the file cannot be found
     */
    public static Manifest getManifest(String path) throws IOException {
        File jar = new File(path);
        JarFile jf = new JarFile(new File(path));
        Manifest mf = new JarFile(jar).getManifest();
        jf.close();
        return mf;
    }

    /**
     * Returns an attribute in the manifest file of the given compiled JAR file at runtime
     *
     * @param filePath      Path of the file to retrieve the manifest for
     * @param attributePath Path of the attribute to find
     * @return The value of a attribute as specified in the manifest of the given JAR file path
     * @throws java.io.IOException if the given file path cannot be found
     */
    public static String getAttribute(String filePath, String attributePath) throws IOException {
        Attributes attributes = getManifest(filePath).getMainAttributes();
        return attributes.getValue(attributePath);
    }

    /**
     * Returns an attribute in the manifest file of this compiled JAR file at runtime
     *
     * @param attributePath Path of the attribute to find
     * @return The value of a attribute as specified in the manifest of this JAR file
     * @throws java.io.IOException         if the given file path cannot be found
     * @throws java.net.URISyntaxException if the given attribute path cannot be converted to a valid URI
     */
    public static String getAttribute(String attributePath) throws URISyntaxException, IOException {
        return getAttribute(ManifestUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), attributePath);
    }
}