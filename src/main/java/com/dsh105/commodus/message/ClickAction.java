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
import java.net.URL;

public interface ClickAction<R> extends Action<R> {

    @Override
    org.spongepowered.api.text.action.ClickAction<R> getSpongeAction();
    
    abstract class AbstractClickAction<R> extends AbstractAction<R> implements ClickAction<R> {

        @Override
        public String getEventName() {
            return "click";
        }
    }

    abstract class PerformCommand extends AbstractClickAction<String> {

        @Override
        public String getId() {
            return Actions.Click.PERFORM_COMMAND;
        }

        @Override
        public org.spongepowered.api.text.action.ClickAction.RunCommand getSpongeAction() {
            return new org.spongepowered.api.text.action.ClickAction.RunCommand() {

                @Override
                public String getId() {
                    return PerformCommand.this.getId();
                }

                @Override
                public String getResult() {
                    return PerformCommand.this.getResult();
                }
            };
        }
    }

    abstract class SuggestCommand extends AbstractClickAction<String> {

        @Override
        public String getId() {
            return Actions.Click.SUGGEST_COMMAND;
        }

        @Override
        public org.spongepowered.api.text.action.ClickAction.SuggestCommand getSpongeAction() {
            return new org.spongepowered.api.text.action.ClickAction.SuggestCommand() {

                @Override
                public String getId() {
                    return ClickAction.SuggestCommand.this.getId();
                }

                @Override
                public String getResult() {
                    return ClickAction.SuggestCommand.this.getResult();
                }
            };
        }
    }

    abstract class ChangePage extends AbstractClickAction<Integer> {

        @Override
        public String getId() {
            return Actions.Click.CHANGE_PAGE;
        }

        @Override
        public org.spongepowered.api.text.action.ClickAction.ChangePage getSpongeAction() {
            return new org.spongepowered.api.text.action.ClickAction.ChangePage() {

                @Override
                public String getId() {
                    return ClickAction.ChangePage.this.getId();
                }

                @Override
                public Integer getResult() {
                    return ClickAction.ChangePage.this.getResult();
                }
            };
        }

    }

    abstract class OpenUrl extends AbstractClickAction<URL> {

        @Override
        public String getId() {
            return Actions.Click.OPEN_URL;
        }

        @Override
        public org.spongepowered.api.text.action.ClickAction.OpenUrl getSpongeAction() {
            return new org.spongepowered.api.text.action.ClickAction.OpenUrl() {

                @Override
                public String getId() {
                    return ClickAction.OpenUrl.this.getId();
                }

                @Override
                public URL getResult() {
                    return ClickAction.OpenUrl.this.getResult();
                }
            };
        }

    }



}