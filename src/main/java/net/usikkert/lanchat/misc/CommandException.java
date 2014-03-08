
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

/**
 * This is used to give the application user direct feedback when doing
 * an illegal operation. Don't use technical messages.
 *
 * @author Shouvik Goswami
 */
public class CommandException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a CommandException with no message or cause.
     */
    public CommandException() {
        super();
    }

    /**
     * Creates a CommandException with the specified message and cause.
     *
     * @param message The exception message to use.
     * @param cause The cause of the exception.
     */
    public CommandException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a CommandException with the specified message.
     *
     * @param message The exception message to use.
     */
    public CommandException(final String message) {
        super(message);
    }

    /**
     * Creates a CommandException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public CommandException(final Throwable cause) {
        super(cause);
    }
}
