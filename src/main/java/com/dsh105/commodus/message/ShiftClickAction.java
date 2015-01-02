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

import java.io.IOException;

public interface ShiftClickAction<R> extends Action<R> {

    @Override
    org.spongepowered.api.text.action.ShiftClickAction<?> getSpongeAction();

    abstract class AbstractShiftClickAction<R> extends AbstractAction<R> implements ShiftClickAction<R> {

        @Override
        public String getEventName() {
            return null;
        }
    }
    
    abstract class InsertText extends AbstractShiftClickAction<String> {

        @Override
        public String getId() {
            return Actions.ShiftClick.INSERT_TEXT;
        }

        @Override
        public JsonWriter writeJson(JsonWriter writer) throws IOException {
            writer.name(getId()).value(getResultString());
            return writer;
        }

        @Override
        public org.spongepowered.api.text.action.ShiftClickAction.InsertText getSpongeAction() {
            return new org.spongepowered.api.text.action.ShiftClickAction.InsertText() {

                @Override
                public String getId() {
                    return ShiftClickAction.InsertText.this.getId();
                }

                @Override
                public String getResult() {
                    return ShiftClickAction.InsertText.this.getResult();
                }
            };
        }
    }
}