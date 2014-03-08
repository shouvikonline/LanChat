
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

package net.usikkert.lanchat.ui.console;

import net.usikkert.lanchat.ui.ChatWindow;

/**
 * This is the console implementation of the chat window.
 *
 * @author Shouvik Goswami
 */
public class ConsoleChatWindow implements ChatWindow {

    /**
     * Shows messages with a simple <code>System.out.println()</code>.
     *
     * @param message The message to write to the console.
     * @param color The color of the message - not implemented.
     */
    @Override
    public void appendToChat(final String message, final int color) {
        System.out.println(message);
    }
}
