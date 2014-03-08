
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import net.usikkert.lanchat.util.Validate;

/**
 * This class has a list of all the supported smileys.
 *
 * @author Shouvik Goswami
 */
public class SmileyMap {

    /** The map linking the smiley code with the smiley image. */
    private final Map<String, ImageIcon> smileyMap;

    /**
     * Constructor. Puts all the smileys in the map.
     *
     * @param imageLoader The image loader.
     */
    public SmileyMap(final ImageLoader imageLoader) {
        Validate.notNull(imageLoader, "Image loader can not be null");

        // Map smiley codes to icons
        smileyMap = new HashMap<String, ImageIcon>();
        smileyMap.put(":)", imageLoader.getSmileIcon());
        smileyMap.put(":(", imageLoader.getSadIcon());
        smileyMap.put(":p", imageLoader.getTongueIcon());
        smileyMap.put(":D", imageLoader.getTeethIcon());
        smileyMap.put(";)", imageLoader.getWinkIcon());
        smileyMap.put(":O", imageLoader.getOmgIcon());
        smileyMap.put(":@", imageLoader.getAngryIcon());
        smileyMap.put(":S", imageLoader.getConfusedIcon());
        smileyMap.put(";(", imageLoader.getCryIcon());
        smileyMap.put(":$", imageLoader.getEmbarrassedIcon());
        smileyMap.put("8)", imageLoader.getShadeIcon());
    }

    /**
     * Gets the smiley with the specified key.
     *
     * @param key The key for the smiley to get.
     * @return The smiley with the specified key.
     */
    public ImageIcon getSmiley(final String key) {
        return smileyMap.get(key);
    }

    /**
     * Gets a set of all the smiley codes.
     *
     * @return A set of all the smiley codes.
     */
    public Set<String> getTextSmileys() {
        return smileyMap.keySet();
    }
}
