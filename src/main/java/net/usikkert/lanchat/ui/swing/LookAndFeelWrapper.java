
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

import javax.swing.UIManager.LookAndFeelInfo;

/**
 * A simple wrapper class for a {@link LookAndFeelInfo}.
 *
 * <p>Useful in lists which uses toString() to show the name of the contents.</p>
 *
 * @author Shouvik Goswami
 */
public class LookAndFeelWrapper {

    /** The wrapped LookAndFeelInfo. */
    private final LookAndFeelInfo lookAndFeelInfo;

    /**
     * Constructor.
     *
     * @param lookAndFeelInfo The LookAndFeelInfo to wrap.
     */
    public LookAndFeelWrapper(final LookAndFeelInfo lookAndFeelInfo) {
        this.lookAndFeelInfo = lookAndFeelInfo;
    }

    /**
     * Gets the wrapped LookAndFeelInfo.
     *
     * @return The wrapped LookAndFeelInfo.
     */
    public LookAndFeelInfo getLookAndFeelInfo() {
        return lookAndFeelInfo;
    }

    /**
     * Overridden to return the name of the wrapped LookAndFeelInfo.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return lookAndFeelInfo.getName();
    }
}
