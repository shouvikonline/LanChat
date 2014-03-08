
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

import java.util.Arrays;
import java.util.List;

import net.usikkert.lanchat.misc.Controller;
import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.net.ConnectionWorker;
import net.usikkert.lanchat.util.Validate;

/**
 * Class for getting instances of JMX MBeans.
 *
 * <p>The following beans are registered:</p>
 *
 * <ul>
 *   <li>{@link NetworkInformation}</li>
 *   <li>{@link ControllerInformation}</li>
 *   <li>{@link GeneralInformation}</li>
 * </ul>
 *
 * @author Shouvik Goswami
 */
public class JMXBeanLoader {

    private final List<JMXBean> jmxBeans;

    /**
     * Initializes the bean loader, and the JMX beans.
     *
     * @param controller The controller.
     * @param connectionWorker The connection worker.
     * @param settings The settings.
     */
    public JMXBeanLoader(final Controller controller, final ConnectionWorker connectionWorker,
                         final Settings settings) {
        Validate.notNull(controller, "Controller can not be null");
        Validate.notNull(connectionWorker, "ConnectionWorker can not be null");
        Validate.notNull(settings, "Settings can not be null");

        jmxBeans = Arrays.asList(
                new NetworkInformation(connectionWorker, settings),
                new ControllerInformation(controller),
                new GeneralInformation(settings));
    }

    public List<JMXBean> getJMXBeans() {
        return jmxBeans;
    }
}
