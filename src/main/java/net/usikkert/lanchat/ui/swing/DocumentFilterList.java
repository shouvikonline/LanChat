
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.usikkert.lanchat.util.Validate;

/**
 * This is a document filter list that can be used when more than one
 * document filter is needed.
 *
 * <p>This filter does not make any changes to the text. The text is added
 * to the document, and then a list of sub-filters are notified so they can
 * do their magic.</p>
 *
 * @author Shouvik Goswami
 */
public class DocumentFilterList extends DocumentFilter {

    /** A list of sub-filters that are notified when text is added. */
    private final List<DocumentFilter> filters;

    /**
     * Constructor.
     */
    public DocumentFilterList() {
        filters = new ArrayList<DocumentFilter>();
    }

    /**
     * Inserts the text at the end of the Document, and notifies the sub-filters.
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized void insertString(final FilterBypass fb, final int offset, final String text,
            final AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, text, attr);

        for (final DocumentFilter filter : filters) {
            filter.insertString(fb, offset, text, attr);
        }
    }

    /**
     * Adds the document filter for notification when text is added.
     *
     * @param filter The document filter to add.
     */
    public synchronized void addDocumentFilter(final DocumentFilter filter) {
        Validate.notNull(filter, "Document filter can not be null");
        filters.add(filter);
    }

    /**
     * Removes the document filter from the notification list.
     *
     * @param filter The document filter to remove.
     */
    public synchronized void removeDocumentFilter(final DocumentFilter filter) {
        Validate.notNull(filter, "Document filter can not be null");
        filters.remove(filter);
    }
}
