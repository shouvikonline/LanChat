
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

package net.usikkert.lanchat;

import net.usikkert.lanchat.ui.swing.debug.CheckThreadViolationRepaintManager;

/**
 * Starts {@link LanChat} with the {@link CheckThreadViolationRepaintManager}, for debugging.
 *
 * @author Christian Ihle
 */
public final class LanChatDebug {

    private LanChatDebug() {

    }

    /**
     * Initializes the repaint manager before running LanChat.
     *
     * @param args Arguments to be passed along to the real main method.
     */
    public static void main(final String[] args) {
        new CheckThreadViolationRepaintManager();
        LanChat.main(args);
    }
}
