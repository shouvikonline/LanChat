
/***************************************************************************
 *   Copyright 2006-2013 by Shouvik Goswwami                               *
 *   shouvik.goswami@gmail.com                                             *
 *                                                                         *
 *   This file is part of LanChat.                                         *
 *                                                                         *
 *   LanChat is free software; you can redistribute it and/or modify       *
 *   it under the terms of the GNU Lesser General Public License as        *
 *   published by the Free Software Foundation, either version 3 of        *
 *   the License, or (at your option) any later version.                   *
 *                                                                         *
 *   LanChat is distributed in the hope that it will be useful,            *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU      *
 *   Lesser General Public License for more details.                       *
 *                                                                         *
 *   You should have received a copy of the GNU Lesser General Public      *
 *   License along with LanChat.                                           *
 *   If not, see <http://www.gnu.org/licenses/>.                           *
 ***************************************************************************/

package net.usikkert.lanchat.misc;

import static org.mockito.Mockito.*;

import net.usikkert.lanchat.ui.ChatWindow;
import net.usikkert.lanchat.ui.UserInterface;
import net.usikkert.lanchat.util.TestUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link MessageController}.
 *
 * @author Christian Ihle
 */
public class MessageControllerTest {

    private MessageController messageController;

    private ChatLogger chatLogger;

    @Before
    public void setUp() {
        messageController = new MessageController(mock(ChatWindow.class), mock(UserInterface.class), mock(Settings.class));

        chatLogger = mock(ChatLogger.class);
        TestUtils.setFieldValue(messageController, "cLog", chatLogger);
    }

    @Test
    public void shutdownShouldCloseTheChatLogger() {
        messageController.shutdown();

        verify(chatLogger).close();
    }
}
