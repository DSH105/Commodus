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

import com.dsh105.commodus.exceptions.ManifestAttributeNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class ManifestUtil {

    private ManifestUtil() {
    }

    public static java.util.jar.Manifest getManifest(String path) throws IOException {
        File jar = new File(path);
        JarFile jf = new JarFile(new File(path));
        java.util.jar.Manifest mf = new JarFile(jar).getManifest();
        jf.close();
        return mf;
    }

    public static String getAttribute(String attributePath) {
        try {
            String filePath = ManifestUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            Attributes attributes = getManifest(filePath).getMainAttributes();
            return attributes.getValue(attributePath);
        } catch (Exception e) {
            throw new ManifestAttributeNotFoundException(e);
        }
    }
}