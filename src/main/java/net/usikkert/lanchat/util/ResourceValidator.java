
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class validates if any resources are missing.
 *
 * @author Shouvik Goswami
 */
public class ResourceValidator {

    /** The map of resources to validate. */
    private final Map<String, URL> resourceMap;

    /**
     * Constructor.
     */
    public ResourceValidator() {
        resourceMap = new HashMap<String, URL>();
    }

    /**
     * Clears all the added resources from the validation list.
     */
    public void clearResources() {
        resourceMap.clear();
    }

    /**
     * Adds a resource to be validated.
     *
     * @param resource The resource to check.
     * @param location The location of the resource.
     */
    public void addResource(final URL resource, final String location) {
        resourceMap.put(location, resource);
    }

    /**
     * Checks if any of the resources were not loaded, and returns a
     * string with a list of the locations of the missing resources.
     * Each location is on a separate line.
     *
     * @return A list of the resources that has not been loaded.
     */
    public String validate() {
        final StringBuilder missingResourceList = new StringBuilder();

        for (final String location : resourceMap.keySet()) {
            if (resourceMap.get(location) == null) {
                if (missingResourceList.length() != 0) {
                    missingResourceList.append("\n");
                }

                missingResourceList.append(location);
            }
        }

        return missingResourceList.toString();
    }
}
