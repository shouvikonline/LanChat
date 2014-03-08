
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.usikkert.lanchat.util.Validate;

/**
 * This is the panel located to the right in the application.
 *
 * These buttons are shown:
 *
 * <ul>
 *   <li>Clear</li>
 *   <li>Away</li>
 *   <li>Topic</li>
 *   <li>Minimize</li>
 * </ul>
 *
 * @author Shouvik Goswami
 */
public class ButtonPanel extends JPanel implements ActionListener {

    /** Standard version uid. */
    private static final long serialVersionUID = 1L;

    /** The minimize button. Minimizes the application to the system tray. */
    private final JButton minimizeB;

    /** The clear button. Clears the text in the main chat. */
    private final JButton clearB;

    /** The away button. Changes the away state of the user. */
    private final JButton awayB;

    /** The topic button. Changes the topic in the main chat. */
    private final JButton topicB;

    /** The mediator. */
    private Mediator mediator;

    /**
     * Constructor.
     */
    public ButtonPanel() {
        setLayout(new GridLayout(4, 1));

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        clearB.setToolTipText("Clear all the text in the chat area.");
        add(clearB);

        awayB = new JButton("Away");
        awayB.addActionListener(this);
        awayB.setToolTipText("Set/unset your user as away.");
        add(awayB);

        topicB = new JButton("Topic");
        topicB.addActionListener(this);
        topicB.setToolTipText("Change the topic of this chat.");
        add(topicB);

        minimizeB = new JButton("Minimize");
        minimizeB.addActionListener(this);
        minimizeB.setToolTipText("Minimize to the system tray.");
        add(minimizeB);

        setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 1));
    }

    /**
     * Sets the mediator to use.
     *
     * @param mediator The mediator to set.
     */
    public void setMediator(final Mediator mediator) {
        Validate.notNull(mediator, "Mediator can not be null");
        this.mediator = mediator;
    }

    /**
     * Enables or disabled the away button.
     *
     * @param away If away, the button is disabled. Else enabled.
     */
    public void setAwayState(final boolean away) {
        topicB.setEnabled(!away);
    }

    /**
     * The listener for button clicks.
     *
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == minimizeB) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mediator.minimize();
                }
            });
        }

        else if (e.getSource() == clearB) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mediator.clearChat();
                }
            });
        }

        else if (e.getSource() == awayB) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mediator.setAway();
                }
            });
        }

        else if (e.getSource() == topicB) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mediator.setTopic();
                }
            });
        }
    }
}
