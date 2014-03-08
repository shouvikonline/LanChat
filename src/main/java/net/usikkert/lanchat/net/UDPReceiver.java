
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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.event.ReceiverListener;
import net.usikkert.lanchat.misc.ErrorHandler;
import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.util.Validate;

/**
 * Receives UDP packets sent directly to the IP address
 * of this machine.
 *
 * @author Shouvik Goswami
 */
public class UDPReceiver implements Runnable {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(UDPReceiver.class.getName());

    /** The datagram socket used for receiving messages. */
    private DatagramSocket udpSocket;

    /** The listener getting all the messages received here. */
    private ReceiverListener listener;

    /** If connected to the network or not. */
    private boolean connected;

    /** The error handler for registering important messages. */
    private final ErrorHandler errorHandler;

    /** The application user. */
    private final User me;

    /**
     * Default constructor.
     *
     * @param settings The settings to use.
     */
    public UDPReceiver(final Settings settings) {
        Validate.notNull(settings, "Settings can not be null");

        errorHandler = ErrorHandler.getErrorHandler();
        me = settings.getMe();
    }

    /**
     * The run() method of this thread. Checks for new packets,
     * extracts the message and IP address, and notifies the listener.
     */
    public void run() {
        while (connected) {
            try {
                final DatagramPacket packet = new DatagramPacket(
                        new byte[Constants.NETWORK_PACKET_SIZE], Constants.NETWORK_PACKET_SIZE);

                udpSocket.receive(packet);
                final String ip = packet.getAddress().getHostAddress();
                final String message = new String(packet.getData(), Constants.MESSAGE_CHARSET).trim();
                LOG.log(Level.FINE, "Message arrived from " + ip + ": " + message);

                if (listener != null) {
                    listener.messageArrived(message, ip);
                }
            }

            // Happens when socket is closed, or network is down
            catch (final IOException e) {
                if (connected) {
                    LOG.log(Level.WARNING, e.toString());
                }

                else {
                    LOG.log(Level.FINE, e.toString());
                }
            }
        }
    }

    /**
     * Creates a new UDP socket, and starts a thread listening
     * on the UDP port. If the UDP port is in use, a new port will be
     * tried instead.
     */
    public void startReceiver() {
        LOG.log(Level.FINE, "Connecting...");

        if (connected) {
            LOG.log(Level.FINE, "Already connected.");
        }

        else {
            int port = Constants.NETWORK_PRIVCHAT_PORT;
            int counter = 0;

            while (counter < 50 && !connected) {
                try {
                    udpSocket = new DatagramSocket(port);
                    connected = true;

                    // The background thread watching for messages from the network.
                    final Thread worker = new Thread(this, "UDPReceiverWorker");
                    worker.start();

                    me.setPrivateChatPort(port);
                    LOG.log(Level.FINE, "Connected to port " + port);
                }

                catch (final IOException e) {
                    LOG.log(Level.SEVERE, e.toString() + " " + port);

                    counter++;
                    port++;
                    me.setPrivateChatPort(0);
                }
            }

            if (!connected) {
                final String error = "Failed to initialize udp network:" +
                        "\nNo available listening port between " + Constants.NETWORK_PRIVCHAT_PORT +
                        " and " + (port - 1) + "." +
                        "\n\nYou will not be able to receive private messages!";

                LOG.log(Level.SEVERE, error);
                errorHandler.showError(error);
            }
        }
    }

    /**
     * Closes the UDP socket, and stops the thread.
     */
    public void stopReceiver() {
        LOG.log(Level.FINE, "Disconnecting...");

        if (!connected) {
            LOG.log(Level.FINE, "Not connected.");
        }

        else {
            connected = false;

            if (udpSocket != null && !udpSocket.isClosed()) {
                udpSocket.close();
            }

            LOG.log(Level.FINE, "Disconnected.");
        }
    }

    /**
     * Sets the listener who will receive all the messages
     * from the UDP packets.
     *
     * @param listener The object to register as a listener.
     */
    public void registerReceiverListener(final ReceiverListener listener) {
        this.listener = listener;
    }
}
