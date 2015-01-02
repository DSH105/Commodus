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
import com.dsh105.commodus.ServerBrand;
import com.dsh105.commodus.ServerUtil;
import com.dsh105.commodus.Transformer;
import com.dsh105.commodus.bukkit.BukkitServerUtil;
import com.dsh105.commodus.container.PlayerContainer;
import com.dsh105.commodus.reflection.Reflection;
import com.google.gson.stream.JsonWriter;
import org.bukkit.entity.Player;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.message.MessageBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class CommodusMessage implements Message {

    private String content;
    private MessageColor color;
    private MessageStyle[] styles;
    private List<Message> children;
    private HoverAction<?> hoverAction;
    private ClickAction<?> clickAction;
    private ShiftClickAction<?> shiftClickAction;

    private String json;
    private org.spongepowered.api.text.message.Message spongeMessage;

    protected CommodusMessage(String content, MessageColor color, MessageStyle[] styles, List<Message> children, HoverAction<?> hoverAction, ClickAction<?> clickAction, ShiftClickAction<?> shiftClickAction) {
        this.content = content;
        this.color = color;
        this.styles = styles;
        this.children = children;
        this.hoverAction = hoverAction;
        this.clickAction = clickAction;
        this.shiftClickAction = shiftClickAction;
    }

    @Override
    public void send(PlayerContainer player) {
        switch (ServerUtil.getServerBrand().getCapsule()) {
            case BUKKIT:
                if (ServerUtil.getPlatformVersion().isCompatible("1.7")) {
                    Object chatComponent = Reflection.invokeStatic(MessageUtil.CHAT_FROM_JSON, toJson());
                    Object packet = Reflection.newInstance(Reflection.getConstructor(MessageUtil.CHAT_PACKET_CLASS, Reflection.getNMSClass("IChatBaseComponent")), chatComponent);
                    BukkitServerUtil.sendPacket(packet, player.asBukkit());
                } else {
                    player.asBukkit().sendMessage(toLegacy());
                }
                break;
            case SPONGE:
                player.asSponge().sendMessage(toSponge());
        }
    }

    @Override
    public void send(PlayerContainer... players) {
        for (PlayerContainer player : players) {
            send(player);
        }
    }

    @Override
    public void send(Iterable<PlayerContainer> players) {
        for (PlayerContainer player : players) {
            send(player);
        }
    }

    @Override
    public void send(ChatType chatType, PlayerContainer player) {
        if (ServerUtil.getServerBrand().getCapsule() != ServerBrand.Capsule.SPONGE) {
            send(player);
            return;
        }
        player.asSponge().sendMessage(chatType, toSponge());
    }

    @Override
    public void send(ChatType chatType, PlayerContainer... players) {
        for (PlayerContainer player : players) {
            send(chatType, player);
        }
    }

    @Override
    public void send(ChatType chatType, Iterable<PlayerContainer> players) {
        for (PlayerContainer player : players) {
            send(chatType, player);
        }
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<Message> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public Collection<Message> children() {
        Deque<Message> children = new ArrayDeque<>();
        children.add(this);
        // recursive
        for (Message child : this.children) {
            children.addAll(child.children());
        }
        return children;
    }

    @Override
    public MessageColor getColor() {
        return color;
    }

    @Override
    public MessageStyle[] getStyles() {
        return styles;
    }

    @Override
    public HoverAction<?> getHoverAction() {
        return hoverAction;
    }

    @Override
    public ClickAction<?> getClickAction() {
        return clickAction;
    }

    @Override
    public ShiftClickAction<?> getShiftClickAction() {
        return shiftClickAction;
    }

    @Override
    public String toLegacy() {
        StringBuilder builder = new StringBuilder();
        if (color != null) {
            builder.append(MessageUtil.COLOR_CHAR + color.getLegacyChar());
        }
        for (MessageStyle style : styles) {
            builder.append(MessageUtil.COLOR_CHAR + style.getLegacyChar());
        }
        builder.append(content);
        for (Message child : children) {
            builder.append(child.toLegacy());
        }
        return builder.toString();
    }

    @Override
    public String toJson() {
        if (json == null) {
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = new JsonWriter(stringWriter);

            try {
                writeJson(writer.beginObject());
                if (children.size() > 0) {
                    writer.name("extra").beginArray();
                    for (Message child : children()) {
                        if (!child.equals(this)) {
                            child.writeJson(writer.beginObject());
                            writer.endObject();
                        }
                    }
                    writer.endArray();
                }
                writer.endObject();
            } catch (IOException e) {
                throw new IllegalStateException("Invalid message content", e);
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            json = stringWriter.toString();
        }
        return json;
    }

    @Override
    public JsonWriter writeJson(JsonWriter writer) throws IOException {
        writer.name("text").value(content);
        if (color != null) {
            writer.name("color").value(color.name().toLowerCase());
        }
        for (MessageStyle style : styles) {
            writer.name(style.name().toLowerCase()).value(true);
        }
        for (Action<?> action : new Action[]{hoverAction, clickAction, shiftClickAction}) {
            if (action != null) {
                action.writeJson(writer);
            }
        }
        return writer;
    }
    
    @Override
    public org.spongepowered.api.text.message.Message toSponge() {
        if (spongeMessage == null) {
            MessageBuilder builder = org.spongepowered.api.text.message.Messages.builder(getContent());
            builder.color(color.getSpongeColor())
                    .style(GeneralUtil.transform(TextStyle.class, styles, new Transformer<MessageStyle, TextStyle>() {

                        @Override
                        public TextStyle transform(MessageStyle transmutable) {
                            return transmutable.getSpongeStyle();
                        }
                    }))
                    .onHover(hoverAction.getSpongeAction())
                    .onClick(clickAction.getSpongeAction())
                    .onShiftClick(shiftClickAction.getSpongeAction());
            for (Message message : children) {
                builder.append(message.toSponge());
            }
            spongeMessage = builder.build();
        }
        return spongeMessage;
    }
}