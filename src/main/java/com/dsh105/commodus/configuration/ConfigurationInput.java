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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ConfigurationInput {

    // TODO: docs

    private String configContent;
    private int totalComments;
    private InputStream input;

    protected ConfigurationInput(String configContent, int totalComments) {
        this.configContent = configContent;
        this.totalComments = totalComments;
        this.input = new ByteArrayInputStream(configContent.getBytes(Charset.forName("UTF-8")));
    }

    public String getContent() {
        return configContent;
    }

    public InputStream getInput() {
        return input;
    }

    public int getTotalComments() {
        return totalComments;
    }
}