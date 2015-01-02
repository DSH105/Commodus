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

import com.dsh105.commodus.GeneralUtil;
import com.dsh105.commodus.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

public final class Actions {

    public static final class Click {

        public static final String PERFORM_COMMAND = "run_command";
        public static final String SUGGEST_COMMAND = "suggest_command";
        public static final String CHANGE_PAGE = "change_page";
        public static final String OPEN_URL = "open_url";

        public static ClickAction<?> of(JsonElement jsonElement) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String action = jsonObject.get("action").getAsString();
            final String value = jsonObject.get("value").getAsString();
            switch (action) {
                case PERFORM_COMMAND:
                    return new ClickAction.PerformCommand() {

                        @Override
                        public String getResult() {
                            return value;
                        }
                    };
                case SUGGEST_COMMAND:
                    return new ClickAction.SuggestCommand() {

                        @Override
                        public String getResult() {
                            return value;
                        }
                    };
                case CHANGE_PAGE:
                    final int page = GeneralUtil.toInteger(value);
                    return new ClickAction.ChangePage() {

                        @Override
                        public Integer getResult() {
                            return page;
                        }
                    };
                case OPEN_URL:
                    return new ClickAction.OpenUrl() {

                        @Override
                        public URL getResult() {
                            try {
                                return new URL(value);
                            } catch (MalformedURLException e) {
                                throw new RuntimeException("Invalid URL: " + value, e);
                            }
                        }
                    };
            }
            return null;
        }
    }

    public static final class Hover {

        public static final String SHOW_TEXT = "show_text";
        public static final String SHOW_ACHIEVEMENT = "show_achievement";
        public static final String SHOW_ITEM = "show_item";
        public static final String SHOW_ENTITY = "show_entity";

        public static HoverAction<?> of(JsonElement jsonElement) {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            String action = jsonObject.get("action").getAsString();
            switch (action) {
                case SHOW_TEXT:
                    return new HoverAction.ShowText() {

                        @Override
                        public String getResult() {
                            return jsonObject.get("value").getAsString();
                        }
                    };
                case SHOW_ACHIEVEMENT:
                    // TODO
                case SHOW_ITEM:
                    // TODO
                case SHOW_ENTITY:
                    JsonObject entityObject = jsonObject.get("value").getAsJsonObject();
                    final EntityInfo entityInfo = new EntityInfo(StringUtil.convertUUID(entityObject.get("id").getAsString()), entityObject.get("name").getAsString(), entityObject.get("type").getAsString());
                    return new HoverAction.ShowEntity() {

                        @Override
                        public EntityInfo getResult() {
                            return entityInfo;
                        }
                    };
            }

            return null;
        }
    }

    public static final class ShiftClick {

        public static final String INSERT_TEXT = "insertion";

    }
}