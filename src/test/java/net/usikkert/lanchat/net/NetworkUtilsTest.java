
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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test of {@link NetworkUtils}.
 *
 * @author Christian Ihle
 */
public class NetworkUtilsTest {

    /**
     * Tests if 2 network interfaces are the same.
     *
     * <p>NetworkInterface is a final class, and can't be mocked easily.
     * So this test will only work when there are at least 2 network interfaces available.</p>
     */
    @Test
    public void testSameNetworkInterface() {
        assertFalse(NetworkUtils.sameNetworkInterface(null, null));

        final Enumeration<NetworkInterface> networkInterfaces = NetworkUtils.getNetworkInterfaces();

        if (networkInterfaces != null) {
            try {
                final NetworkInterface interface1 = networkInterfaces.nextElement();
                final NetworkInterface interface2 = networkInterfaces.nextElement();

                assertTrue(NetworkUtils.sameNetworkInterface(interface1, interface1));
                assertTrue(NetworkUtils.sameNetworkInterface(interface2, interface2));

                assertFalse(NetworkUtils.sameNetworkInterface(interface1, interface2));
                assertFalse(NetworkUtils.sameNetworkInterface(interface1, null));
                assertFalse(NetworkUtils.sameNetworkInterface(null, interface2));
            }

            catch (final NoSuchElementException e) {
                System.err.println("Not enough network interfaces - aborting test");
            }
        }

        else {
            System.err.println("No network interfaces - aborting test");
        }
    }

    /**
     * Tests that the hostname returned is the correct name of the localhost.
     */
    @Test
    public void testGetLocalHostName() {
        try {
            final InetAddress localHostAddress = InetAddress.getLocalHost(); // Could throw exception

            final String localHostName = NetworkUtils.getLocalHostName();
            assertNotNull("Name of localhost should not be null", localHostName);
            final InetAddress addressByName = InetAddress.getByName(localHostName);
            assertEquals("The addresses should be equal", localHostAddress, addressByName);
        }

        catch (final UnknownHostException e) {
            System.err.println("Could not get localhost - aborting test: " + e.toString());
        }
    }

    @Test
    @Ignore("Machine specific test")
    public void getNetworkInterfaceByName() {
        final NetworkInterface eth0 = NetworkUtils.getNetworkInterfaceByName("eth0");
        System.out.println(NetworkUtils.getNetworkInterfaceInfo(eth0));
    }
}
