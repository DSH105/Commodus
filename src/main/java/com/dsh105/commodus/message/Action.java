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

package com.dsh105.commodus.message;

import com.google.gson.stream.JsonWriter;
import org.spongepowered.api.text.action.TextAction;

import java.io.IOException;

public interface Action<R> extends JsonWritable {

    String getId();
    
    R getResult();
    
    String getResultString();
    
    String getEventName();
    
    TextAction<?> getSpongeAction();
    
    abstract class AbstractAction<R> implements Action<R> {

        @Override
        public String getResultString() {
            return getResult().toString();
        }
        
        @Override
        public JsonWriter writeJson(JsonWriter writer) throws IOException {
            writer.name(getEventName() + "Event").beginObject().name("action").value(getId()).name("value").value(getResultString()).endObject();
            return writer;
        }
    }
}