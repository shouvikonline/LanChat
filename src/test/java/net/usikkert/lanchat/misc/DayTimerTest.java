
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

package net.usikkert.lanchat.misc;

import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Timer;

import net.usikkert.lanchat.ui.UserInterface;
import net.usikkert.lanchat.util.TestUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link DayTimer}.
 *
 * @author Christian Ihle
 */
public class DayTimerTest {

    private DayTimer dayTimer;

    private Timer timer;

    @Before
    public void setUp() {
        dayTimer = new DayTimer(mock(UserInterface.class));

        timer = mock(Timer.class);
        TestUtils.setFieldValue(dayTimer, "timer", timer);
    }

    @Test
    public void startTimerShouldScheduleAtFixedRate() {
        dayTimer.startTimer();

        verify(timer).scheduleAtFixedRate(eq(dayTimer), any(Date.class), eq(1000L * 60L * 60L));
    }

    @Test
    public void stopTimerShouldCancel() {
        dayTimer.stopTimer();

        verify(timer).cancel();
    }
}
