
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

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.util.Validate;

/**
 * Registers JMX MBeans.
 *
 * <p>Connect to <code>LanChat</code> with <code>JConsole</code> to get access
 * to the MBeans. <code>JConsole</code> is part of the Java SDK.</p>
 *
 * @author Shouvik Goswami
 */
public class JMXAgent {

    private static final Logger LOG = Logger.getLogger(JMXAgent.class.getName());

    /**
     * Default constructor. Registers the MBeans, and logs any failures.
     *
     * @param jmxBeanLoader The bean loader containing the JMX MBeans to register and activate.
     */
    public JMXAgent(final JMXBeanLoader jmxBeanLoader) {
        Validate.notNull(jmxBeanLoader, "JMXBeanLoader can not be null");

        final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        final List<JMXBean> jmxBeans = jmxBeanLoader.getJMXBeans();

        try {
            for (JMXBean jmxBean : jmxBeans) {
                registerJMXBean(mBeanServer, jmxBean);
            }
        }

        catch (final JMException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void registerJMXBean(final MBeanServer mBeanServer, final JMXBean jmxBean) throws JMException {
        final ObjectName generalInfoName = createObjectName(jmxBean);
        mBeanServer.registerMBean(jmxBean, generalInfoName);
    }

    private ObjectName createObjectName(final JMXBean jmxBean) throws JMException {
        return new ObjectName(Constants.APP_NAME + ":name=" + jmxBean.getBeanName());
    }
}
