
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

import javax.swing.ImageIcon;

import net.usikkert.lanchat.util.Validate;

/**
 * This class represents a smiley in some text with position,
 * the text code, and the icon for the smiley.
 *
 * @author Shouvik Goswami
 */
public class Smiley {

    /** The position of the first character in the smiley. */
    private final int startPosition;

    /** The position of the last character in the smiley. */
    private final int stopPosition;

    /** The icon replacing the text smiley code. */
    private final ImageIcon icon;

    /** The text smiley code. */
    private final String code;

    /**
     * Constructor.
     *
     * @param startPosition The position of the first character in the smiley.
     * @param icon The icon replacing the text smiley code.
     * @param code The text smiley code.
     */
    public Smiley(final int startPosition, final ImageIcon icon, final String code) {
        Validate.notNull(icon, "Icon can not be null");
        Validate.notEmpty(code, "Code can not be empty");

        this.startPosition = startPosition;
        this.icon = icon;
        this.code = code;

        stopPosition = startPosition + code.length();
    }

    /**
     * Gets the position of the first character in the smiley.
     *
     * @return The position of the first character in the smiley.
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Gets the position of the last character in the smiley.
     *
     * @return The position of the last character in the smiley.
     */
    public int getStopPosition() {
        return stopPosition;
    }

    /**
     * Gets the icon replacing the text smiley.
     *
     * @return The icon replacing the text smiley.
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Gets the text smiley code.
     *
     * @return The text smiley code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the smiley code, and the position of the smiley in the text.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return code + " [" + startPosition + "," + stopPosition + "]";
    }
}
