
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

import java.util.Arrays;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.util.Tools;
import net.usikkert.lanchat.util.Validate;

/**
 * The valid startup arguments for the application.
 *
 * @author Shouvik Goswami
 */
public enum Argument {

    CONSOLE("-c", "--console", "Starts " + Constants.APP_NAME + " in console mode", false),
    DEBUG("-d", "--debug", "Starts " + Constants.APP_NAME + " with verbose debug output enabled", false),
    HELP("-h", "--help", "Shows this help message", false),
    VERSION("-v", "--version", "Shows version information", false),
    NO_PRIVATE_CHAT(null, "--no-private-chat", "Disables private chat", false),
    ALWAYS_LOG(null, "--always-log", "Enables logging, without option to disable", false),
    LOG_LOCATION(null, "--log-location", "Location to store log files", true),
    UNKNOWN(null, null, null, false);

    private final String shortArgumentName;
    private final String fullArgumentName;
    private final String description;
    private final boolean requiresValue;

    Argument(final String shortArgumentName, final String fullArgumentName, final String description,
             final boolean requiresValue) {
        this.shortArgumentName = shortArgumentName;
        this.fullArgumentName = fullArgumentName;
        this.description = description;
        this.requiresValue = requiresValue;
    }

    /**
     * Checks if the argument in the parameter is equal to "this" enum argument.
     *
     * @param argument The argument to compare with.
     * @return If they are equal.
     */
    public boolean isEqualTo(final String argument) {
        Validate.notNull(argument, "Argument can not be null");

        if (argument.equals(shortArgumentName) || argument.equals(fullArgumentName)) {
            return true;
        }

        return requiresValue && argument.startsWith(fullArgumentName + "=");
    }

    @Override
    public String toString() {
        return fullArgumentName;
    }

    String getArgument() {
        if (shortArgumentName != null) {
            return fullArgumentName + " (" + shortArgumentName + ")";
        }

        if (requiresValue) {
            return fullArgumentName + "=<value>";
        }

        return fullArgumentName;
    }

    /**
     * Returns a formatted list of all the arguments with short name, full name and description.
     * One argument on each line.
     *
     * @return String with all the arguments.
     */
    public static String getArgumentsAsString() {
        final Argument[] arguments = getValidArguments();
        final StringBuilder builder = new StringBuilder();

        for (final Argument argument : arguments) {
            builder.append("\n ");
            builder.append(Tools.postPadString(argument.getArgument(), 24));
            builder.append(argument.description);
        }

        return builder.toString().replaceFirst("\n", "");
    }

    static Argument[] getValidArguments() {
        final Argument[] arguments = Argument.values();
        return Arrays.copyOf(arguments, arguments.length - 1); // Skips UNKNOWN
    }
}
