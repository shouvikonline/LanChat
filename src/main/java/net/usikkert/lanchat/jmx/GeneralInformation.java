
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

package net.usikkert.lanchat.jmx;

import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.util.Validate;

/**
 * This is a JMX MBean for general information.
 *
 * @author Shouvik Goswami
 */
public class GeneralInformation implements GeneralInformationMBean {

    /** The application user. */
    private final User me;

    public GeneralInformation(final Settings settings) {
        Validate.notNull(settings, "Settings can not be null");

        me = settings.getMe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String about() {
        final StringBuilder sb = new StringBuilder();

        sb.append("Client: " + me.getClient() + "\n");
        sb.append("User name: " + me.getNick() + "\n");
        sb.append("IP address: " + me.getIpAddress() + "\n");
        sb.append("Host name: " + me.getHostName() + "\n");
        sb.append("Operating System: " + me.getOperatingSystem());

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBeanName() {
        return "General";
    }
}
