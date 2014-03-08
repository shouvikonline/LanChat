
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

package net.usikkert.lanchat.ui.swing;

import static org.mockito.Mockito.*;

import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.net.FileTransfer.Direction;
import net.usikkert.lanchat.net.MockFileTransfer;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the {@link TransferDialog}.
 *
 * @author Christian Ihle
 */
public class TransferDialogTest {

    private ImageLoader imageLoader;
    private Settings settings;

    @Before
    public void setUp() {
        final User me = new User("Me", 123);
        me.setIpAddress("192.168.1.2");

        settings = mock(Settings.class);
        when(settings.getMe()).thenReturn(me);

        imageLoader = new ImageLoader();
    }

    /**
     * Creates a {@link TransferDialog} for receiving a file,
     * and simulates the file transfer.
     *
     * @throws InterruptedException In case of sleep issues.
     */
    @Test
    public void testReceiveDialog() throws InterruptedException {
        final MockMediator mediator = new MockMediator();
        final MockFileTransfer fileTransfer = new MockFileTransfer(Direction.RECEIVE);

        new TransferDialog(mediator, fileTransfer, imageLoader, settings);

        // Returns true when the close button is clicked
        while (!mediator.isClose()) {
            Thread.sleep(100);
        }
    }

    /**
     * Creates a {@link TransferDialog} for sending a file,
     * and simulates the file transfer.
     *
     * @throws InterruptedException In case of sleep issues.
     */
    @Test
    public void testSendDialog() throws InterruptedException {
        final MockMediator mediator = new MockMediator();
        final MockFileTransfer fileTransfer = new MockFileTransfer(Direction.SEND);

        new TransferDialog(mediator, fileTransfer, imageLoader, settings);

        // Returns true when the close button is clicked
        while (!mediator.isClose()) {
            Thread.sleep(100);
        }
    }
}
