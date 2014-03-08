
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

package net.usikkert.lanchat.util;

/**
 * Contains utility methods for validating input.
 *
 * @author Shouvik Goswami
 */
public final class Validate {

    /**
     * Private constructor. Only static methods in this class.
     */
    private Validate() {

    }

    /**
     * Checks if <code>obj</code> is <code>null</code>, and throws
     * an {@link IllegalArgumentException} if that is true.
     *
     * @param obj The object to check.
     * @param errorMsg The error message to use in the exception.
     */
    public static void notNull(final Object obj, final String errorMsg) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * Checks if <code>text</code> is <code>null</code> or empty,
     * and throws an {@link IllegalArgumentException} if that is true.
     *
     * @param text The string to check.
     * @param errorMsg The error message to use in the exception.
     */
    public static void notEmpty(final String text, final String errorMsg) {
        if (text == null || text.trim().length() == 0) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
