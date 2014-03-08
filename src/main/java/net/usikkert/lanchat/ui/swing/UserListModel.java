
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

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import net.usikkert.lanchat.event.UserListListener;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.misc.UserList;

/**
 * This is the list model for the user list. It's just a facade above
 * the real list containing the users, so it can deliver events on changes.
 *
 * @author Shouvik Goswami
 */
public class UserListModel extends AbstractListModel implements UserListListener {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The real list of users. */
    private final UserList userList;

    /**
     * Constructor. Adds this list model as a listener for events
     * from the real user list.
     *
     * @param userList The list where the real users are.
     */
    public UserListModel(final UserList userList) {
        this.userList = userList;
        userList.addUserListListener(this);
    }

    /**
     * Returns the user at the specified index position.
     *
     * {@inheritDoc}
     */
    @Override
    public User getElementAt(final int index) {
        return userList.get(index);
    }

    /**
     * Returns the number of users in the user list.
     *
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return userList.size();
    }

    /**
     * Sends a fireIntervalAdded() event.
     *
     * {@inheritDoc}
     */
    @Override
    public void userAdded(final int pos, final User user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fireIntervalAdded(this, pos, pos);
            }
        });
    }

    /**
     * Sends a fireContentsChanged() event.
     *
     * {@inheritDoc}
     */
    @Override
    public void userChanged(final int pos, final User user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fireContentsChanged(this, pos, pos);
            }
        });
    }

    /**
     * Sends a fireIntervalRemoved() event.
     *
     * {@inheritDoc}
     */
    @Override
    public void userRemoved(final int pos, final User user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fireIntervalRemoved(this, pos, pos);
            }
        });
    }
}
