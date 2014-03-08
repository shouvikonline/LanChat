
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

import net.usikkert.lanchat.misc.Controller;
import net.usikkert.lanchat.util.Validate;

/**
 * This is a JMX MBean for the controller.
 *
 * @author Shouvik Goswami
 */
public class ControllerInformation implements ControllerInformationMBean {

    /** The controller. */
    private final Controller controller;

    /**
     * Constructor.
     *
     * @param controller The controller.
     */
    public ControllerInformation(final Controller controller) {
        Validate.notNull(controller, "Controller can not be null");
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logOn() {
        controller.logOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logOff() {
        controller.logOff(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBeanName() {
        return "Controller";
    }
}
