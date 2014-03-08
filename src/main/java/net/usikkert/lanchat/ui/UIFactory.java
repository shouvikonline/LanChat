
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

package net.usikkert.lanchat.ui;

import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

import net.usikkert.lanchat.ui.console.LanChatConsole;
import net.usikkert.lanchat.ui.swing.LanChatFrame;

/**
 * This factory decides which User Interface to load.
 *
 * @author Shouvik Goswami
 */
public class UIFactory {

    private boolean done;

    /**
     * Loads the User Interface matching the ui argument.
     *
     * @param choice Which ui to load.
     * Two choices are available at this moment: 'swing' and 'console'.
     *
     * @throws UIException If a ui has already been loaded, or if an
     * unknown ui type was requested, or if no graphical environment was detected.
     */
    public void loadUI(final UIChoice choice) throws UIException {
        if (done) {
            throw new UIException("A User Interface has already been loaded.");
        }

        else {
            if (choice == UIChoice.SWING) {
                if (GraphicsEnvironment.isHeadless()) {
                    throw new UIException("The Swing User Interface could not be loaded" +
                            " because a graphical environment could not be detected.");
                }

                else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new LanChatFrame();
                        }
                    });

                    done = true;
                }
            }

            else if (choice == UIChoice.CONSOLE) {
                new LanChatConsole();
                done = true;
            }

            else {
                throw new UIException("Unknown User Interface requested.");
            }
        }
    }
}
