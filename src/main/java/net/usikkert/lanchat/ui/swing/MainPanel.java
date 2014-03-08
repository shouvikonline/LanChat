
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

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.autocomplete.AutoCompleter;
import net.usikkert.lanchat.misc.CommandHistory;
import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.ui.ChatWindow;
import net.usikkert.lanchat.util.Validate;

/**
 * This is the panel containing the main chat area, the input field,
 * and the {@link SidePanel} on the right side.
 * <br><br>
 * The chat area has url recognition, and a right click menu. The input
 * field has tab-completion, command history, and a right click menu.
 *
 * @author Shouvik Goswami
 */
public class MainPanel extends JPanel implements ActionListener, CaretListener, ChatWindow, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MainPanel.class.getName());

    private final JScrollPane chatSP;
    private final JTextPane chatTP;
    private final MutableAttributeSet chatAttr;
    private final StyledDocument chatDoc;
    private final JTextField msgTF;
    private final CommandHistory cmdHistory;
    private AutoCompleter autoCompleter;
    private Mediator mediator;

    /**
     * Constructor. Creates the panel.
     *
     * @param sideP The panel on the right, containing the user list and the buttons.
     * @param imageLoader The image loader.
     * @param settings The settings to use.
     */
    public MainPanel(final SidePanel sideP, final ImageLoader imageLoader, final Settings settings) {
        Validate.notNull(sideP, "Side panel can not be null");
        Validate.notNull(imageLoader, "Image loader can not be null");
        Validate.notNull(settings, "Settings can not be null");

        setLayout(new BorderLayout(2, 2));

        chatTP = new JTextPane();
        chatTP.setEditable(false);
        chatTP.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        chatTP.setEditorKit(new MiddleAlignedIconViewEditorKit());
        chatTP.setBackground(UIManager.getColor("TextPane.background"));

        chatSP = new JScrollPane(chatTP);
        chatSP.setMinimumSize(new Dimension(290, 200));
        chatAttr = new SimpleAttributeSet();
        chatDoc = chatTP.getStyledDocument();

        final URLMouseListener urlML = new URLMouseListener(chatTP, settings);
        chatTP.addMouseListener(urlML);
        chatTP.addMouseMotionListener(urlML);

        final DocumentFilterList documentFilterList = new DocumentFilterList();
        documentFilterList.addDocumentFilter(new URLDocumentFilter(false));
        documentFilterList.addDocumentFilter(new SmileyDocumentFilter(false, imageLoader, settings));
        final AbstractDocument doc = (AbstractDocument) chatDoc;
        doc.setDocumentFilter(documentFilterList);

        msgTF = new JTextField();
        msgTF.addActionListener(this);
        msgTF.addCaretListener(this);
        msgTF.addKeyListener(this);

        // Make sure tab generates key events
        msgTF.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                new HashSet<AWTKeyStroke>());

        final AbstractDocument msgDoc = (AbstractDocument) msgTF.getDocument();
        msgDoc.setDocumentFilter(new SizeDocumentFilter(Constants.MESSAGE_MAX_BYTES));

        add(chatSP, BorderLayout.CENTER);
        add(sideP, BorderLayout.EAST);
        add(msgTF, BorderLayout.SOUTH);

        new CopyPastePopup(msgTF);
        new CopyPopup(chatTP);

        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        cmdHistory = new CommandHistory();
    }

    /**
     * Sets the mediator to use in the listeners.
     *
     * @param mediator The mediator to use.
     */
    public void setMediator(final Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Sets the ready-to-use autocompleter for the input field.
     *
     * @param autoCompleter The autocompleter to use.
     */
    public void setAutoCompleter(final AutoCompleter autoCompleter) {
        this.autoCompleter = autoCompleter;
    }

    /**
     * Adds the message to the chat area, in the chosen color.
     *
     * @param message The message to append.
     * @param color The color to use for the message.
     */
    @Override
    public void appendToChat(final String message, final int color) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    StyleConstants.setForeground(chatAttr, new Color(color));
                    chatDoc.insertString(chatDoc.getLength(), message + "\n", chatAttr);
                    chatTP.setCaretPosition(chatDoc.getLength());
                }

                catch (final BadLocationException e) {
                    LOG.log(Level.SEVERE, e.toString(), e);
                }
            }
        });
    }

    /**
     * Gets the chat area.
     *
     * @return The chat area.
     */
    public JTextPane getChatTP() {
        return chatTP;
    }

    /**
     * Gets the chat area's scrollpane.
     *
     * @return The chat area's scrollpane.
     */
    public JScrollPane getChatSP() {
        return chatSP;
    }

    /**
     * Clears all the text from the chat area.
     */
    public void clearChat() {
        chatTP.setText("");
    }

    /**
     * Gets the input field.
     *
     * @return The input field.
     */
    public JTextField getMsgTF() {
        return msgTF;
    }

    /**
     * Updates the write status after the caret has moved.
     *
     * {@inheritDoc}
     */
    @Override
    public void caretUpdate(final CaretEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mediator.updateWriting();
            }
        });
    }

    /**
     * When enter is pressed in the input field, the text is added to the
     * command history, and the mediator shows the text in the chat area.
     *
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        // The input field
        if (e.getSource() == msgTF) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    cmdHistory.add(msgTF.getText());
                    mediator.write();
                }
            });
        }
    }

    /**
     * When tab is pressed while in the input field, the word at the
     * caret position will be autocompleted if any suggestions are found.
     *
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent ke) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tab-completion
                if (ke.getKeyCode() == KeyEvent.VK_TAB && ke.getModifiers() == 0) {
                    if (autoCompleter != null) {
                        final int caretPos = msgTF.getCaretPosition();
                        final String orgText = msgTF.getText();
                        final String newText = autoCompleter.completeWord(orgText, caretPos);

                        if (newText.length() > 0) {
                            msgTF.setText(newText);
                            msgTF.setCaretPosition(autoCompleter.getNewCaretPosition());
                        }
                    }
                }
            }
        });
    }

    /**
     * Not implemented.
     *
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(final KeyEvent ke) {

    }

    /**
     * After some text has been added to the command history, it can
     * be accessed by browsing through the history with the up and down
     * keys while focus is on the input field.
     *
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent ke) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Command history up
                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    final String up = cmdHistory.goUp();

                    if (!msgTF.getText().equals(up)) {
                        msgTF.setText(up);
                    }
                }

                // Command history down
                else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    final String down = cmdHistory.goDown();

                    if (!msgTF.getText().equals(down)) {
                        msgTF.setText(down);
                    }
                }
            }
        });
    }
}
