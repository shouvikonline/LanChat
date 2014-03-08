
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
 * This class keeps some information about the current state of the chat.
 *
 * @author Shouvik Goswami
 */
public class ChatState {

    /** Information about the current topic in the chat. */
    private final Topic topic;

    /** Whether the application user was writing at the moment this was updated. */
    private boolean wrote;

    /** Whether the client is logged on to the chat. */
    private boolean loggedOn;

    /** Whether the client has completed the logon procedure. */
    private boolean logonCompleted;

    /**
     * Constructor.
     */
    public ChatState() {
        topic = new Topic();
        wrote = false;
        loggedOn = false;
        logonCompleted = false;
    }

    /**
     * Returns if the application user wrote the last time this was updated.
     *
     * @return True if the application user wrote the last time this was updated.
     */
    public boolean isWrote() {
        return wrote;
    }

    /**
     * Sets if the application user is writing at this moment.
     *
     * @param wrote True if the application user is writing at this moment.
     */
    public void setWrote(final boolean wrote) {
        this.wrote = wrote;
    }

    /**
     * Gets the object containing the current topic information.
     *
     * @return The current topic.
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Checks if the user has logged on to the chat.
     *
     * @return If the user has logged on.
     */
    public boolean isLoggedOn() {
        return loggedOn;
    }

    /**
     * Sets if the user has logged on to the chat.
     *
     * @param loggedOn If the user has logged on.
     */
    public void setLoggedOn(final boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    /**
     * Checks if the procedure to logon to the chat has completed.
     *
     * @return If the logon is complete.
     */
    public boolean isLogonCompleted() {
        return logonCompleted;
    }

    /**
     * Sets if the procedure to logon to the chat has completed.
     *
     * @param logonCompleted If the logon is complete.
     */
    public void setLogonCompleted(final boolean logonCompleted) {
        this.logonCompleted = logonCompleted;
    }
}
