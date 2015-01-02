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

import com.dsh105.commodus.container.AchievementContainer;
import com.dsh105.commodus.container.EntityContainer;
import com.dsh105.commodus.container.ItemStackContainer;
import com.dsh105.commodus.container.StatisticContainer;
import com.google.gson.stream.JsonWriter;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.IOException;

public interface HoverAction<R> extends Action<R> {

    @Override
    org.spongepowered.api.text.action.HoverAction<?> getSpongeAction();

    abstract class AbstractHoverAction<R> extends AbstractAction<R> implements HoverAction<R> {

        @Override
        public String getEventName() {
            return "hover";
        }
    }

    abstract class ShowText extends AbstractHoverAction<String> {

        @Override
        public String getId() {
            return Actions.Hover.SHOW_TEXT;
        }

        @Override
        public org.spongepowered.api.text.action.HoverAction.ShowText getSpongeAction() {
            return new org.spongepowered.api.text.action.HoverAction.ShowText() {

                @Override
                public String getId() {
                    return HoverAction.ShowText.this.getId();
                }

                @Override
                public org.spongepowered.api.text.message.Message getResult() {
                    return org.spongepowered.api.text.message.Messages.of(HoverAction.ShowText.this.getResult());
                }
            };
        }
    }

    abstract class ShowAchievement extends AbstractHoverAction<AchievementContainer> {

        @Override
        public String getId() {
            return Actions.Hover.SHOW_ACHIEVEMENT;
        }

        @Override
        public String getResultString() {
            return "achievement." + getResult().getAchievementName();
        }

        @Override
        public org.spongepowered.api.text.action.HoverAction.ShowAchievement getSpongeAction() {
            return new org.spongepowered.api.text.action.HoverAction.ShowAchievement() {

                @Override
                public String getId() {
                    return HoverAction.ShowAchievement.this.getId();
                }

                @Override
                public Object getResult() {
                    // TODO:
                    return null;
                }
            };
        }
    }

    abstract class ShowStatistic extends AbstractHoverAction<StatisticContainer> {

        @Override
        public String getId() {
            return Actions.Hover.SHOW_ACHIEVEMENT;
        }

        @Override
        public String getResultString() {
            return "stat." + getResult().getStatisticName();
        }

        @Override
        public org.spongepowered.api.text.action.HoverAction.ShowAchievement getSpongeAction() {
            return new org.spongepowered.api.text.action.HoverAction.ShowAchievement() {

                @Override
                public String getId() {
                    return HoverAction.ShowStatistic.this.getId();
                }

                @Override
                public Object getResult() {
                    // TODO:
                    return null;
                }
            };
        }
    }

    abstract class ShowItem extends AbstractHoverAction<ItemStackContainer> {

        @Override
        public String getId() {
            return Actions.Hover.SHOW_ITEM;
        }

        @Override
        public String getResultString() {
            // TODO: implement this
            return super.getResultString();
        }
        
        @Override
        public org.spongepowered.api.text.action.HoverAction.ShowItem getSpongeAction() {
            return new org.spongepowered.api.text.action.HoverAction.ShowItem() {

                @Override
                public String getId() {
                    return HoverAction.ShowItem.this.getId();
                }

                @Override
                public ItemStack getResult() {
                    return HoverAction.ShowItem.this.getResult().asSponge();
                }
            };
        }
    }

    abstract class ShowEntity extends AbstractHoverAction<EntityInfo> {

        @Override
        public String getId() {
            return Actions.Hover.SHOW_ENTITY;
        }

        @Override
        public String getResultString() {
            // TODO: implement this instead of writeJson(...)?
            return super.getResultString();
        }

        @Override
        public JsonWriter writeJson(JsonWriter writer) throws IOException {
            EntityInfo result = getResult();
            writer.name(getEventName() + "Event").beginObject()
                    .name("action").value(getId())
                    .name("value").beginObject()
                    // begin entity data
                    .name("name").value(result.getEntityName())
                    .name("type").value(result.getEntityTypeId())
                    .name("id").value(result.getEntityUID().toString())
                    .endObject().endObject();
            return writer;
        }
        
        @Override
        public org.spongepowered.api.text.action.HoverAction.ShowEntity getSpongeAction() {
            return new org.spongepowered.api.text.action.HoverAction.ShowEntity() {

                @Override
                public String getId() {
                    return HoverAction.ShowEntity.this.getId();
                }

                @Override
                public Entity getResult() {
                    return HoverAction.ShowEntity.this.getResult().asSponge();
                }
            };
        }
    }
}