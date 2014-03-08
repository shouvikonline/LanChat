
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

package net.usikkert.lanchat.argument;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test of {@link Argument}.
 *
 * @author Christian Ihle
 */
public class ArgumentTest {

    @Test
    public void getValidArgumentsShouldReturnAllArgumentsExceptUnknown() {
        final Argument[] validArguments = Argument.getValidArguments();

        assertEquals(7, validArguments.length);

        assertEquals(Argument.CONSOLE, validArguments[0]);
        assertEquals(Argument.DEBUG, validArguments[1]);
        assertEquals(Argument.HELP, validArguments[2]);
        assertEquals(Argument.VERSION, validArguments[3]);
        assertEquals(Argument.NO_PRIVATE_CHAT, validArguments[4]);
        assertEquals(Argument.ALWAYS_LOG, validArguments[5]);
        assertEquals(Argument.LOG_LOCATION, validArguments[6]);
    }

    @Test
    public void getArgumentsAsStringShouldIncludeDetailsFromAllValidArguments() {
        final String argumentsAsString = Argument.getArgumentsAsString();

        final String expected =
                " --console (-c)          Starts LanChat in console mode\n" +
                " --debug (-d)            Starts LanChat with verbose debug output enabled\n" +
                " --help (-h)             Shows this help message\n" +
                " --version (-v)          Shows version information\n" +
                " --no-private-chat       Disables private chat\n" +
                " --always-log            Enables logging, without option to disable\n" +
                " --log-location=<value>  Location to store log files";

        assertEquals(expected, argumentsAsString);
    }

    @Test
    public void isEqualToShouldHandleBothShortAndFullArgumentName() {
        assertTrue(Argument.CONSOLE.isEqualTo("-c"));
        assertTrue(Argument.CONSOLE.isEqualTo("--console"));

        assertFalse(Argument.CONSOLE.isEqualTo("-h"));
        assertFalse(Argument.CONSOLE.isEqualTo("--help"));
    }
}
