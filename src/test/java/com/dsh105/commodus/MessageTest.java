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

package com.dsh105.commodus;

import com.dsh105.commodus.message.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    private Message message;

    @Before
    public void build() {
        MessageBuilder builder = Messages.builder();
        builder.content("Lorem ipsum").color(MessageColor.AQUA).style(MessageStyle.ITALIC).onClick(new ClickAction.SuggestCommand() {

            @Override
            public String getResult() {
                return "/test";
            }
        });
        builder.append(Messages.of(" ipsum"));
        builder.append(Messages.builder(" dolor").style(MessageStyle.OBFUSCATED).onShiftClick(new ShiftClickAction.InsertText() {

            @Override
            public String getResult() {
                return "Hello world!";
            }
        }).build());
        builder.append(Messages.builder(" sit amet").onHover(new HoverAction.ShowText() {

            @Override
            public String getResult() {
                return ":)";
            }
        }).build());
        message = builder.build();
    }

    @Test
    public void testMessageSerialization() {
        // If serialisation/deserialisation is working correctly, these two will be equal
        Assert.assertEquals(message.toJson(), Messages.fromJson(message.toJson()).toJson());
    }
}