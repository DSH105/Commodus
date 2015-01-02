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

import com.dsh105.commodus.Affirm;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class CommodusMessageBuilder implements MessageBuilder {

    private static final JsonParser jsonParser = new JsonParser();

    private String content;
    private MessageColor color;
    private List<MessageStyle> styles = new ArrayList<>();
    private List<Message> children = new ArrayList<>();
    private HoverAction<?> hoverAction;
    private ClickAction<?> clickAction;
    private ShiftClickAction<?> shiftClickAction;

    protected CommodusMessageBuilder() {

    }

    @Override
    public MessageBuilder append(Message... children) {
        if (children != null) { // handle null case
            Collections.addAll(this.children, children);
        }
        return this;
    }

    @Override
    public MessageBuilder append(Iterable<Message> children) {
        Affirm.notNull(children);
        for (Message message : children) {
            this.children.add(message);
        }
        return this;
    }

    @Override
    public MessageBuilder content(String content) {
        Affirm.notNull(content);
        this.content = content;
        return this;
    }

    @Override
    public MessageBuilder color(MessageColor color) {
        // may be null
        this.color = color;
        return this;
    }

    @Override
    public MessageBuilder style(MessageStyle... styles) {
        if (styles != null) {
            Collections.addAll(this.styles, styles);
        }
        return this;
    }

    @Override
    public MessageBuilder onHover(HoverAction<?> hoverAction) {
        // may be null
        this.hoverAction = hoverAction;
        return this;
    }

    @Override
    public MessageBuilder onClick(ClickAction<?> clickAction) {
        // may be null
        this.clickAction = clickAction;
        return this;
    }

    @Override
    public MessageBuilder onShiftClick(ShiftClickAction<?> shiftClickAction) {
        // may be null
        this.shiftClickAction = shiftClickAction;
        return this;
    }

    @Override
    public MessageBuilder fromJson(String json) {
        JsonObject parent = jsonParser.parse(json).getAsJsonObject();
        applyJsonTo(this, parent);
        JsonArray children = parent.getAsJsonArray("extra");

        if (children != null) {
            for (JsonElement serialisedElement : children) {
                append(applyJsonTo(Messages.builder(), serialisedElement.getAsJsonObject()).build());
            }
        }
        return this;
    }

    @Override
    public Message build() {
        return new CommodusMessage(content, color, styles.toArray(new MessageStyle[0]), children, hoverAction, clickAction, shiftClickAction);
    }

    private MessageBuilder applyJsonTo(MessageBuilder to, JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            final JsonElement element = entry.getValue();

            if (key.equals("text")) {
                to.content(element.getAsString());
            } else if (Arrays.asList(MessageStyle.styles()).contains(key)) {
                if (element.getAsBoolean()) {
                    to.style(MessageStyle.valueOf(key.toUpperCase()));
                }
            } else if (key.equals("color")) {
                to.color(MessageColor.valueOf(element.getAsString().toUpperCase()));
            } else if (key.equals("clickEvent")) {
                to.onClick(Actions.Click.of(element));
            } else if (key.equals("hoverEvent")) {
                to.onHover(Actions.Hover.of(element));
            } else if (key.equals("insertion")) {
                to.onShiftClick(new ShiftClickAction.InsertText() {

                    @Override
                    public String getResult() {
                        return element.getAsString();
                    }
                });
            }
        }
        return to;
    }
}