
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

package net.usikkert.lanchat.net;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.NetworkInterface;
import java.util.Enumeration;

import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link OperatingSystemNetworkInfo}.
 *
 * @author Christian Ihle
 */
public class OperatingSystemNetworkInfoTest {

    private Settings settings;

    @Before
    public void setUp() {
        settings = mock(Settings.class);
        when(settings.getMe()).thenReturn(new User("testuser", 123));
    }

    /**
     * Tests if the network interface for the operating system can be found.
     *
     * <p>But only if there are usable network interfaces available.</p>
     */
    @Test
    public void testFindingTheOSNetworkInterface() {
        final Enumeration<NetworkInterface> networkInterfaces = NetworkUtils.getNetworkInterfaces();
        final OperatingSystemNetworkInfo osNicInfo = new OperatingSystemNetworkInfo(settings);
        final NetworkInterface osInterface = osNicInfo.getOperatingSystemNetworkInterface();

        if (networkInterfaces == null) {
            System.err.println("No network interfaces found.");
            assertNull(osInterface);
            return;
        }

        boolean validNetworkAvailable = false;

        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();

            if (NetworkUtils.isUsable(networkInterface)) {
                validNetworkAvailable = true;
                break;
            }
        }

        if (!validNetworkAvailable) {
            System.err.println("No usable network interfaces found.");
            assertNull(osInterface);
            return;
        }

        assertNotNull(osInterface);

        // This is known to sometimes fail in Vista. It is unknown why Vista
        // prefers unusable network interfaces.
        assertTrue(NetworkUtils.isUsable(osInterface));
    }
}
