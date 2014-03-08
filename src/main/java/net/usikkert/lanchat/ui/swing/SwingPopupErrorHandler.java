
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

package net.usikkert.lanchat.ui.swing;

import javax.swing.SwingUtilities;

import net.usikkert.lanchat.event.ErrorListener;
import net.usikkert.lanchat.misc.ErrorHandler;

/**
 * This is the implementation of the error listener for use
 * in the swing gui. When an error occurs, a message box is shown.
 *
 * @author Shouvik Goswami
 */
public class SwingPopupErrorHandler implements ErrorListener {

    /**
     * Default constructor. Registers the class as a listener
     * in the error handler.
     */
    public SwingPopupErrorHandler() {
        ErrorHandler.getErrorHandler().addErrorListener(this);
    }

    /**
     * Shows an error message in a non-blocking JOptionPane message box.
     *
     * @param errorMsg The message to show.
     */
    @Override
    public void errorReported(final String errorMsg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UITools.showErrorMessage(errorMsg, "Error");
            }
        });
    }

    /**
     * Shows a critical error message in a JOptionPane message box.
     *
     * @param criticalErrorMsg The message to show.
     */
    @Override
    public void criticalErrorReported(final String criticalErrorMsg) {
        UITools.showErrorMessage(criticalErrorMsg, "Critical Error");
    }
}
