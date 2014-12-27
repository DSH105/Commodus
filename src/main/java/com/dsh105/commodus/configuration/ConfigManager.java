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

package com.dsh105.commodus.configuration;

import java.io.*;

public abstract class ConfigManager<C extends Config> {

    // TODO: docs

    private static String COMMENT_MARKER = "CONFIG_COMMENT_";
    private String commentPrefix = "#";

    public String getCommentPrefix() {
        return commentPrefix;
    }

    public void setCommentPrefix(String commentPrefix) {
        this.commentPrefix = commentPrefix;
    }

    protected abstract File getPluginDirectory();

    public C prepareConfig(String configName, String... header) {
        return prepareConfig(configName, null, header);
    }

    // TODO: deal with resource loading
    public C prepareConfig(String configName, InputStream resourceCopy, String... header) {
        return prepareConfig(new File(getPluginDirectory(), configName), resourceCopy, header);
    }

    public C prepareConfig(File file, String... header) {
        return prepareConfig(file, null, header);
    }

    public C prepareConfig(File file, InputStream resourceCopy, String... header) {
        try {
            if (!file.exists()) {
                create(file, resourceCopy, header);
            }

            return prepare(file, compile(file));
        } catch (IOException e) {
            // failed to create file
            throw new RuntimeException("Failed to prepare configuration file.", e);
        }
    }

    protected abstract C prepare(File file, ConfigurationInput configurationInput);

    public void save(File file, String configString) {
        String configContent = compileWithComments(configString);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(configContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare configuration file.", e);
        }
    }

    public void save(Config config) {
        save(config.getFile(), config.saveToString());
    }

    private void create(File file, InputStream resourceCopy, String... header) throws IOException {
        if (file.exists()) {
            return;
        }

        // Make sure the folder exists
        file.getParentFile().mkdirs();
        file.createNewFile();

        if (resourceCopy != null) {
            // Copy the existing resource

            OutputStream output = null;
            try {
                output = new FileOutputStream(file);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = resourceCopy.read(bytes)) > 0) {
                    output.write(bytes, 0, read);
                }
            } finally {
                if (output != null) {
                    output.close();
                }
                resourceCopy.close();
            }
        }

        // apply header to file
        writeHeader(file, header);
    }

    public void writeHeader(Config config, String... header) throws IOException {
        writeHeader(config.getFile(), header);
    }

    public void writeHeader(File configFile, String... header) throws IOException {
        if (header == null || header.length <= 0) {
            return;
        }

        String line;
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            builder.append("# +----------------------------------------------------+ #\n");

            for (String headerPart : header) {
                if (headerPart.length() > 50) {
                    continue;
                }

                int length = (50 - headerPart.length()) / 2;
                StringBuilder finalLine = new StringBuilder(headerPart);

                for (int i = 0; i < length; i++) {
                    // Complete the rest of the line with spaces

                    finalLine.append(" ");
                    finalLine.reverse();
                    finalLine.append(" ");
                    finalLine.reverse();
                }

                if (headerPart.length() % 2 != 0) {
                    finalLine.append(" ");
                }

                builder.append("# < ");
                builder.append(finalLine.toString());
                builder.append(" > #");
                builder.append('\n');
            }

            builder.append("# +----------------------------------------------------+ #");

            writer.write(compileWithComments(builder.toString()));
        }
    }

    public String compileWithComments(String configString) {
        String[] lines = configString.split("\n");
        StringBuilder builder = new StringBuilder();
        boolean headerStart = true;
        boolean lastCommentLine = true;

        for (String line : lines) {
            if (line.startsWith(COMMENT_MARKER)) {
                String comment = this.commentPrefix + line.trim().substring(line.indexOf(":") + 1);

                if (comment.startsWith("# +-")) {
                    // Header comment
                    builder.append(comment).append('\n');
                    if (headerStart) {
                        builder.append('\n');
                    }
                    headerStart = !headerStart;
                    lastCommentLine = true;
                } else {
                    String commentLine = comment;
                    if (comment.startsWith("# ' ")) {
                        commentLine = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
                    }
                    if (lastCommentLine) {
                        builder.append('\n');
                    }
                    builder.append(commentLine).append('\n');
                    lastCommentLine = false;
                }
            } else {
                // normal line
                builder.append(line).append('\n');
                lastCommentLine = false;
            }
        }

        return builder.toString();
    }

    public ConfigurationInput compile(Config config) throws IOException {
        return compile(config.getFile());
    }

    public ConfigurationInput compile(File configFile) throws IOException {
        if (!configFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            StringBuilder builder = new StringBuilder();

            String line;
            int totalComments = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    line = line.replaceFirst("#", COMMENT_MARKER + totalComments++ + ":");
                }
                builder.append(line).append('\n');
            }

            return new ConfigurationInput(builder.toString(), totalComments);
        }
    }
}