package com.dsh105.simpleutils;

import com.dsh105.simpleutils.exceptions.ManifestAttributeNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class Manifest {

    public static java.util.jar.Manifest getManifest(String path) throws IOException {
        File jar = new File(path);
        JarFile jf = new JarFile(new File(path));
        java.util.jar.Manifest mf = new JarFile(jar).getManifest();
        jf.close();
        return mf;
    }

    public static String getAttribute(String attributePath) {
        try {
            String filePath = Manifest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            Attributes attributes = getManifest(filePath).getMainAttributes();
            return attributes.getValue(attributePath);
        } catch (Exception e) {
            throw new ManifestAttributeNotFoundException(e);
        }
    }
}