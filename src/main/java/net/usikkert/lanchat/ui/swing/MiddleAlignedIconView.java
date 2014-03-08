
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

import javax.swing.text.Element;
import javax.swing.text.IconView;

/**
 * This is an icon view which middle aligns icons with the text,
 * instead of bottom aligning like the normal {@link IconView}.
 *
 * @author Shouvik Goswami
 */
public class MiddleAlignedIconView extends IconView {

    /**
     * Constructor.
     *
     * @param elem The icon.
     */
    public MiddleAlignedIconView(final Element elem) {
        super(elem);
    }

    /**
     * Decides how to align the icon.
     *
     * <p>Options:</p>
     * <ul>
     *   <li><b>0</b> - aligns the top of the image along the bottom of the text.</li>
     *   <li><b>0.25</b> - aligns the middle of the image along the bottom of the text.</li>
     *   <li><b>0.5</b> - aligns the top of the image along the top of the text.</li>
     *   <li><b>0.75</b> - aligns the middle of the image along the middle of the text.</li>
     *   <li><b>1</b> - aligns the bottom of the image along the bottom of the text.</li>
     * </ul>
     *
     * {@inheritDoc}
     */
    @Override
    public float getAlignment(final int axis) {
        return 0.75f;
    }
}
