
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.misc.UserList;
import net.usikkert.lanchat.util.Tools;
import net.usikkert.lanchat.util.Validate;

/**
 * This is the complete right side panel of the application.
 * It consists of the user list, and the button panel.
 *
 * @author Shouvik Goswami
 */
public class SidePanel extends JPanel implements ActionListener, MouseListener, FileDropSource {

    /** The standard version UID. */
    private static final long serialVersionUID = 1L;

    /** The right click popup menu in the user list. */
    private final JPopupMenu userMenu;

    /** The information menu item. */
    private final JMenuItem infoMI;

    /** The send file menu item. */
    private final JMenuItem sendfileMI;

    /** The private chat menu item. */
    private final JMenuItem privchatMI;

    /** The user list. */
    private final JList userL;

    /** The panel with the buttons. */
    private final ButtonPanel buttonP;

    /** The application user. */
    private final User me;

    /** The application settings. */
    private final Settings settings;

    /** Handles drag and drop of files on users. */
    private final FileTransferHandler fileTransferHandler;

    /** Custom model for the user list. */
    private UserListModel userListModel;

    /** The mediator. */
    private Mediator mediator;

    /**
     * Constructor. Creates the panel.
     *
     * @param buttonP The button panel.
     * @param imageLoader The image loader.
     * @param settings The settings to use.
     */
    public SidePanel(final ButtonPanel buttonP, final ImageLoader imageLoader, final Settings settings) {
        Validate.notNull(buttonP, "Button panel can not be null");
        Validate.notNull(imageLoader, "Image loader can not be null");
        Validate.notNull(settings, "Settings can not be null");

        this.buttonP = buttonP;
        this.settings = settings;

        setLayout(new BorderLayout(2, 2));

        fileTransferHandler = new FileTransferHandler(this);
        userL = new JList();
        userL.setCellRenderer(new UserListCellRenderer(imageLoader));
        userL.addMouseListener(this);
        userL.setTransferHandler(fileTransferHandler);
        userL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane userSP = new JScrollPane(userL);

        add(userSP, BorderLayout.CENTER);
        add(buttonP, BorderLayout.SOUTH);

        userMenu = new JPopupMenu();
        infoMI = new JMenuItem("Information");
        infoMI.setMnemonic('I');
        infoMI.addActionListener(this);
        sendfileMI = new JMenuItem("Send file");
        sendfileMI.setMnemonic('S');
        sendfileMI.addActionListener(this);
        privchatMI = new JMenuItem("Private chat");
        privchatMI.setMnemonic('P');
        privchatMI.addActionListener(this);
        privchatMI.setFont(privchatMI.getFont().deriveFont(Font.BOLD)); // default menu item
        userMenu.add(infoMI);
        userMenu.add(sendfileMI);
        userMenu.add(privchatMI);

        setPreferredSize(new Dimension(114, 0));

        me = settings.getMe();
    }

    /**
     * Sets the mediator.
     *
     * @param mediator The mediator to set.
     */
    public void setMediator(final Mediator mediator) {
        Validate.notNull(mediator, "Mediator can not be null");

        this.mediator = mediator;
        fileTransferHandler.setMediator(mediator);
    }

    /**
     * Sets the user list implementation in the user list model.
     *
     * @param userList The user list to set.
     */
    public void setUserList(final UserList userList) {
        Validate.notNull(userList, "User list can not be null");

        userListModel = new UserListModel(userList);
        userL.setModel(userListModel);
    }

    /**
     * Gets the currently selected user.
     *
     * {@inheritDoc}
     */
    @Override
    public User getUser() {
        return (User) userL.getSelectedValue();
    }

    /**
     * Gets the user list.
     *
     * @return The user list.
     */
    public JList getUserList() {
        return userL;
    }

    /**
     * Handles action events on the right click popup menu on the users.
     *
     * <p>Currently:</p>
     * <ul>
     *   <li>Information</li>
     *   <li>Send file</li>
     *   <li>Private chat</li>
     * </ul>
     *
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == infoMI) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final User user = userListModel.getElementAt(userL.getSelectedIndex());
                    String info = "Information about " + user.getNick();

                    if (user.isAway()) {
                        info += " (Away)";
                    }

                    info += ".\n\nIP address: " + user.getIpAddress();

                    if (user.getHostName() != null) {
                        info += "\nHost name: " + user.getHostName();
                    }

                    info += "\nClient: " + user.getClient() +
                            "\nOperating System: " + user.getOperatingSystem() +
                            "\n\nOnline: " + Tools.howLongFromNow(user.getLogonTime());

                    if (user.isAway()) {
                        info += "\nAway message: " + user.getAwayMsg();
                    }

                    UITools.showInfoMessage(info, "Info");
                }
            });
        }

        else if (e.getSource() == sendfileMI) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mediator.sendFile(getUser(), null);
                }
            });
        }

        else if (e.getSource() == privchatMI) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final User user = userListModel.getElementAt(userL.getSelectedIndex());
                    mediator.showPrivChat(user);
                }
            });
        }
    }

    /**
     * Not implemented.
     *
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    /**
     * Not implemented.
     *
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    /**
     * Not implemented.
     *
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(final MouseEvent e) {

    }

    /**
     * Handles mouse pressed events on the user list.
     *
     * <p>If a mouse click happens on a user the user is selected,
     * else the currently selected user is unselected.</p>
     *
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(final MouseEvent e) {
        if (e.getSource() == userL) {
            final Point p = e.getPoint();
            final int index = userL.locationToIndex(p);

            if (index != -1) {
                final Rectangle r = userL.getCellBounds(index, index);

                if (r.x <= p.x && p.x <= r.x + r.width && r.y <= p.y && p.y <= r.y + r.height) {
                    userL.setSelectedIndex(index);
                }

                else {
                    userL.clearSelection();
                }
            }
        }
    }

    /**
     * Handles mouse released events on the user list.
     *
     * <p>Decides which menu items to show on right click on a user,
     * and opens a private chat with the selected user on double click.</p>
     *
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (e.getSource() == userL) {
            // Right click
            if (userMenu.isPopupTrigger(e) && userL.getSelectedIndex() != -1) {
                final User temp = userListModel.getElementAt(userL.getSelectedIndex());

                if (temp.isMe()) {
                    sendfileMI.setVisible(false);
                    privchatMI.setVisible(false);
                }

                else if (temp.isAway() || me.isAway()) {
                    sendfileMI.setVisible(true);
                    sendfileMI.setEnabled(false);
                    privchatMI.setVisible(true);

                    if (!canPrivateChatWithUser(temp)) {
                        privchatMI.setEnabled(false);
                    } else {
                        privchatMI.setEnabled(true);
                    }
                }

                else {
                    sendfileMI.setVisible(true);
                    sendfileMI.setEnabled(true);
                    privchatMI.setVisible(true);

                    if (!canPrivateChatWithUser(temp)) {
                        privchatMI.setEnabled(false);
                    } else {
                        privchatMI.setEnabled(true);
                    }
                }

                userMenu.show(userL, e.getX(), e.getY());
            }

            // Double left click
            else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && userL.getSelectedIndex() != -1) {
                final User user = userListModel.getElementAt(userL.getSelectedIndex());

                if (user != me && canPrivateChatWithUser(user)) {
                    mediator.showPrivChat(user);
                }
            }
        }
    }

    /**
     * Makes sure the contents of this panel is repainted.
     */
    public void repaintPanel() {
        userL.repaint();
        buttonP.repaint();
    }

    private boolean canPrivateChatWithUser(final User user) {
        final boolean privateChatEnabled = !settings.isNoPrivateChat();
        return privateChatEnabled && user.getPrivateChatPort() != 0;
    }
}
