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

public interface MessageBuilder {

    MessageBuilder append(Message... children);
    
    MessageBuilder append(Iterable<Message> children);

    MessageBuilder content(String content);
    
    MessageBuilder color(MessageColor color);
    
    MessageBuilder style(MessageStyle... styles);
    
    MessageBuilder onHover(HoverAction<?> hoverAction);
    
    MessageBuilder onClick(ClickAction<?> clickAction);
    
    MessageBuilder onShiftClick(ShiftClickAction<?> shiftClickAction);

    MessageBuilder fromJson(String json);

    Message build();

}