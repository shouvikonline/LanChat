
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

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.usikkert.lanchat.misc.ChatState;
import net.usikkert.lanchat.misc.CommandException;
import net.usikkert.lanchat.misc.Controller;
import net.usikkert.lanchat.misc.MessageController;
import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.misc.Topic;
import net.usikkert.lanchat.misc.User;
import net.usikkert.lanchat.misc.WaitingList;
import net.usikkert.lanchat.ui.UserInterface;
import net.usikkert.lanchat.util.Tools;
import net.usikkert.lanchat.util.Validate;

/**
 * This class responds to events from the message parser.
 *
 * @author Shouvik Goswami
 */
public class DefaultMessageResponder implements MessageResponder {

    private static final Logger LOG = Logger.getLogger(DefaultMessageResponder.class.getName());

    private final Controller controller;
    private final User me;
    private final TransferList tList;
    private final WaitingList wList;
    private final UserInterface ui;
    private final MessageController msgController;
    private final ChatState chatState;

    /**
     * Constructor.
     *
     * @param controller The controller to use for communication.
     * @param ui The user interface to update.
     * @param settings The settings to use.
     */
    public DefaultMessageResponder(final Controller controller, final UserInterface ui, final Settings settings) {
        Validate.notNull(controller, "Controller can not be null");
        Validate.notNull(ui, "UserInterface can not be null");
        Validate.notNull(settings, "Settings can not be null");

        this.controller = controller;
        this.ui = ui;

        msgController = ui.getMessageController();
        me = settings.getMe();
        tList = controller.getTransferList();
        wList = controller.getWaitingList();
        chatState = controller.getChatState();
    }

    /**
     * Shows a message from a user in the user interface.
     * If the user that sent the message does not yet exist in the user list,
     * the user is asked to identify itself before the message is shown.
     *
     * @param userCode The unique code of the user who sent the message.
     * @param msg The message.
     * @param color The color the message has.
     */
    @Override
    public void messageArrived(final int userCode, final String msg, final int color) {
        // A little hack to stop messages from showing before the user is logged on
        final Thread t = new Thread("DefaultMessageResponderMessageArrived") {
            @Override
            public void run() {
                if (isAlive()) {
                    int counter = 0;

                    while (wList.isWaitingUser(userCode) && counter < 40) {
                        counter++;
                        Tools.sleep(50);
                    }
                }

                if (!controller.isNewUser(userCode)) {
                    final User user = controller.getUser(userCode);

                    if (!user.isAway()) {
                        msgController.showUserMessage(user.getNick(), msg, color);

                        // Visible but not in front
                        if (ui.isVisible() && !ui.isFocused()) {
                            me.setNewMsg(true);
                        }

                        ui.notifyMessageArrived(user);
                    }
                }

                else {
                    LOG.log(Level.SEVERE, "Could not find user: " + userCode);
                }
            }
        };

        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();

            t.start();
        }

        else {
            t.run();
        }
    }

    /**
     * When a user logs off it is removed from the user list, and
     * any open private chat window is notified.
     *
     * @param userCode The unique code of the user who logged off.
     */
    @Override
    public void userLogOff(final int userCode) {
        final User user = controller.getUser(userCode);

        if (user != null) {
            final String logOffMessage = user.getNick() + " logged off";

            controller.removeUser(user, logOffMessage);
            msgController.showSystemMessage(logOffMessage);
        }
    }

    /**
     * When a user logs on, the user is added to the user list.
     * If the user's nick name is not valid, it is reset, and reported if
     * it is identical to the application user's nick.
     *
     * @param newUser The user logging on to the chat.
     */
    @Override
    public void userLogOn(final User newUser) {
        if (me.getNick().trim().equalsIgnoreCase(newUser.getNick())) {
            controller.sendNickCrashMessage(newUser.getNick());
            newUser.setNick("" + newUser.getCode());
        }

        else if (controller.isNickInUse(newUser.getNick())) {
            newUser.setNick("" + newUser.getCode());
        }

        else if (!Tools.isValidNick(newUser.getNick())) {
            newUser.setNick("" + newUser.getCode());
        }

        controller.getUserList().add(newUser);
        msgController.showSystemMessage(newUser.getNick() + " logged on from " + newUser.getIpAddress());
    }

    /**
     * If a user exposes itself without having sent a logon message first,
     * and the expose is not part of the application startup, the user
     * is checked and added to the user list.
     *
     * @param newUser The unknown user.
     */
    private void userShowedUp(final User newUser) {
        if (me.getNick().trim().equalsIgnoreCase(newUser.getNick())) {
            controller.sendNickCrashMessage(newUser.getNick());
            newUser.setNick("" + newUser.getCode());
        }

        else if (controller.isNickInUse(newUser.getNick())) {
            newUser.setNick("" + newUser.getCode());
        }

        else if (!Tools.isValidNick(newUser.getNick())) {
            newUser.setNick("" + newUser.getCode());
        }

        controller.getUserList().add(newUser);
        msgController.showSystemMessage(newUser.getNick() + " showed up unexpectedly from " + newUser.getIpAddress());
    }

    /**
     * Updates the topic, and shows it.
     *
     * @param userCode The unique code of the user who changed the topic.
     * @param newTopic The new topic.
     * @param nick The nick name of the user who changed the topic.
     * @param time The time when the topic was set.
     */
    @Override
    public void topicChanged(final int userCode, final String newTopic, final String nick, final long time) {
        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();
        }

        else {
            if (time > 0 && nick.length() > 0) {
                final Topic topic = controller.getTopic();

                if (newTopic != null) {
                    if (!newTopic.equals(topic.getTopic()) && time > topic.getTime()) {
                        if (chatState.isLogonCompleted()) {
                            msgController.showSystemMessage(nick + " changed the topic to: " + newTopic);
                        }

                        // Shown during startup.
                        else {
                            final String date = Tools.dateToString(new Date(time), "HH:mm:ss, dd. MMM. yy");
                            msgController.showSystemMessage("Topic is: " + newTopic + " (set by " + nick + " at " + date + ")");
                        }

                        topic.changeTopic(newTopic, nick, time);
                        ui.showTopic();
                    }
                }

                else {
                    if (!topic.getTopic().equals(newTopic) && time > topic.getTime() && chatState.isLogonCompleted()) {
                        msgController.showSystemMessage(nick + " removed the topic");
                        topic.changeTopic("", "", time);
                        ui.showTopic();
                    }
                }
            }
        }
    }

    /**
     * Adds unknown users that are exposing themselves.
     * This happens mostly during startup, but can also happen after a timeout.
     *
     * @param user The unknown user who was exposed.
     */
    @Override
    public void userExposing(final User user) {
        if (controller.isNewUser(user.getCode())) {
            // Usually this happens when someone returns from a timeout
            if (chatState.isLogonCompleted()) {
                if (wList.isWaitingUser(user.getCode())) {
                    wList.removeWaitingUser(user.getCode());
                }

                userShowedUp(user);
            }

            // This should ONLY happen during logon
            else {
                controller.getUserList().add(user);
            }
        }

        else {
            final User orgUser = controller.getUser(user.getCode());

            // When users timeout, there can become sync issues
            if (!orgUser.getNick().equals(user.getNick())) {
                nickChanged(user.getCode(), user.getNick());
            }

            if (!orgUser.getAwayMsg().equals(user.getAwayMsg())) {
                awayChanged(user.getCode(), user.isAway(), user.getAwayMsg());
            }
        }
    }

    /**
     * When the user has logged on to the network, the application updates
     * the status.
     *
     * @param ipAddress The IP address of the application user.
     */
    @Override
    public void meLogOn(final String ipAddress) {
        chatState.setLoggedOn(true);
        me.setIpAddress(ipAddress);
        me.setHostName(NetworkUtils.getLocalHostName());
        msgController.showSystemMessage("You logged on as " + me.getNick() + " from " + createHostInfo(me));
        ui.showTopic();
    }

    /**
     * Returns a string containing both the host name and the ip address
     * if the host name is set, or just the ip address if not.
     *
     * @param user The user to get host info from.
     * @return A string with host info.
     */
    private String createHostInfo(final User user) {
        if (user.getHostName() != null) {
            return user.getHostName() + " (" + user.getIpAddress() + ")";
        } else {
            return user.getIpAddress();
        }
    }

    /**
     * Updates the writing status of the user.
     *
     * @param userCode The unique code of the user who started or stopped writing.
     * @param writing If the user is writing or not.
     */
    @Override
    public void writingChanged(final int userCode, final boolean writing) {
        controller.changeWriting(userCode, writing);
    }

    /**
     * Updates the away status for the user, both in the main window
     * and in the private chat window.
     *
     * @param userCode The unique code of the user who changed away status.
     * @param away If the user is away or not.
     * @param awayMsg The away message if the user is away, or an empty string.
     */
    @Override
    public void awayChanged(final int userCode, final boolean away, final String awayMsg) {
        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();
        }

        else {
            try {
                final User user = controller.getUser(userCode);
                controller.changeAwayStatus(userCode, away, awayMsg);

                if (away) {
                    msgController.showSystemMessage(user.getNick() + " went away: " + awayMsg);
                } else {
                    msgController.showSystemMessage(user.getNick() + " came back");
                }

                if (user.getPrivchat() != null) {
                    user.getPrivchat().setAway(away);

                    if (away) {
                        msgController.showPrivateSystemMessage(user, user.getNick() + " went away: " + user.getAwayMsg());
                    } else {
                        msgController.showPrivateSystemMessage(user, user.getNick() + " came back");
                    }
                }
            }

            catch (final CommandException e) {
                LOG.log(Level.SEVERE, "Something very strange going on here...\n" + e);
            }
        }
    }

    /**
     * Updates the idle time of the application user,
     * and checks if the ip address has changed.
     *
     * @param ipAddress The IP address of the application user.
     */
    @Override
    public void meIdle(final String ipAddress) {
        me.setLastIdle(System.currentTimeMillis());

        if (!me.getIpAddress().equals(ipAddress) && chatState.isLoggedOn()) {
            msgController.showSystemMessage("You changed ip from " + me.getIpAddress() + " to " + ipAddress);
            me.setIpAddress(ipAddress);
        }
    }

    /**
     * Updates the idle time of the user,
     * and checks if the user's ip address has changed.
     *
     * @param userCode The unique code of the user who sent the idle message.
     * @param ipAddress The IP address of that user.
     */
    @Override
    public void userIdle(final int userCode, final String ipAddress) {
        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();
        }

        else {
            final User user = controller.getUser(userCode);
            user.setLastIdle(System.currentTimeMillis());

            if (!user.getIpAddress().equals(ipAddress)) {
                msgController.showSystemMessage(user.getNick() + " changed ip from " + user.getIpAddress() + " to " + ipAddress);
                user.setIpAddress(ipAddress);
            }
        }
    }

    /**
     * Sends the current topic.
     */
    @Override
    public void topicRequested() {
        controller.sendTopicRequestedMessage();
    }

    /**
     * Someone sent a message that the application user's nick is
     * in use, so the nick is reset.
     */
    @Override
    public void nickCrash() {
        controller.changeNick(me.getCode(), "" + me.getCode());
        msgController.showSystemMessage("Nick crash, resetting nick to " + me.getNick());
        ui.showTopic();
    }

    /**
     * Sends information about this client to the other clients.
     */
    @Override
    public void exposeRequested() {
        controller.sendExposingMessage();
        controller.sendClientInfo();
    }

    /**
     * Changes the nick name of a user, if valid.
     *
     * @param userCode The unique code of the user who changed nick name.
     * @param newNick The new nick name.
     */
    @Override
    public void nickChanged(final int userCode, final String newNick) {
        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();
        }

        else {
            final User user = controller.getUser(userCode);

            if (!controller.isNickInUse(newNick) && Tools.isValidNick(newNick)) {
                final String oldNick = user.getNick();
                controller.changeNick(userCode, newNick);
                msgController.showSystemMessage(oldNick + " changed nick to " + newNick);

                if (user.getPrivchat() != null) {
                    msgController.showPrivateSystemMessage(user, oldNick + " changed nick to " + user.getNick());
                    user.getPrivchat().updateUserInformation();
                }
            }

            else {
                LOG.log(Level.SEVERE, user.getNick() + " tried to change nick to '" + newNick + "', which is invalid");
            }
        }
    }

    /**
     * Asks if the application user wants to receive a file from another user,
     * and if so, starts a server listening for a file transfer.
     *
     * If the user does not exist in the user list, it's asked to identify
     * itself first.
     *
     * @param userCode The unique code of the user who is asking to send a file.
     * @param byteSize The size of the file in bytes.
     * @param fileName The name of the file.
     * @param user The nick name of the user.
     * @param fileHash The hash code of the file.
     */
    @Override
    public void fileSend(final int userCode, final long byteSize, final String fileName, final String user, final int fileHash) {
        if (controller.isNewUser(userCode)) {
            wList.addWaitingUser(userCode);
            controller.sendExposeMessage();
            controller.sendGetTopicMessage();
        }

        new Thread("DefaultMessageResponderFileSend") {
            @Override
            public void run() {
                int counter = 0;

                while (wList.isWaitingUser(userCode) && counter < 40) {
                    counter++;
                    Tools.sleep(50);
                }

                if (!controller.isNewUser(userCode)) {
                    final String size = Tools.byteToString(byteSize);
                    final User tmpUser = controller.getUser(userCode);
                    final File defaultFile = new File(
                            System.getProperty("user.home") + System.getProperty("file.separator") + fileName);
                    final FileReceiver fileRes = tList.addFileReceiver(tmpUser, defaultFile, byteSize);

                    msgController.showSystemMessage(
                            user + " is trying to send the file " + fileName + " (#" + fileRes.getId() + ") [" + size + "]");

                    if (ui.askFileSave(user, fileName, size)) {
                        ui.showFileSave(fileRes);

                        if (fileRes.isAccepted() && !fileRes.isCanceled()) {
                            ui.showTransfer(fileRes);

                            try {
                                final int port = fileRes.startServer();
                                controller.sendFileAccept(tmpUser, port, fileHash, fileName);

                                if (fileRes.transfer()) {
                                    msgController.showSystemMessage("Successfully received " + fileName +
                                            " from " + user + ", and saved as " + fileRes.getFile().getName());
                                }

                                else {
                                    msgController.showSystemMessage("Failed to receive " + fileName + " from " + user);
                                    fileRes.cancel();
                                }
                            }

                            // Failed to start the server
                            catch (final ServerException e) {
                                LOG.log(Level.SEVERE, e.toString(), e);
                                msgController.showSystemMessage("Failed to receive " + fileName + " from " + user);
                                controller.sendFileAbort(tmpUser, fileHash, fileName);
                                fileRes.cancel();
                            }

                            // Failed to send the accept message
                            catch (final CommandException e) {
                                msgController.showSystemMessage("Failed to receive " + fileName + " from " + user);
                                fileRes.cancel();
                            }
                        }

                        else if (!fileRes.isCanceled()) {
                            msgController.showSystemMessage("You declined to receive " + fileName + " from " + user);
                            controller.sendFileAbort(tmpUser, fileHash, fileName);
                        }

                    }

                    else if (!fileRes.isCanceled()) {
                        msgController.showSystemMessage("You declined to receive " + fileName + " from " + user);
                        controller.sendFileAbort(tmpUser, fileHash, fileName);
                    }

                    tList.removeFileReceiver(fileRes);
                }

                else {
                    LOG.log(Level.SEVERE, "Could not find user: " + user);
                }
            }
        } .start();
    }

    /**
     * The other user stopped a file transfer from the application user,
     * or the other way around.
     * Updates the status in the file sender.
     *
     * @param userCode The unique code of the other user.
     * @param fileName The name of the file.
     * @param fileHash The hash code of the file.
     */
    @Override
    public void fileSendAborted(final int userCode, final String fileName, final int fileHash) {
        final User user = controller.getUser(userCode);
        final FileSender fileSender = tList.getFileSender(user, fileName, fileHash);

        if (fileSender != null) {
            fileSender.cancel();
            msgController.showSystemMessage(user.getNick() + " aborted reception of " + fileName);
            tList.removeFileSender(fileSender);
        }

        final FileReceiver fileReceiver = tList.getFileReceiver(user, fileName);

        if (fileReceiver != null) {
            fileReceiver.cancel();
            msgController.showSystemMessage(user.getNick() + " aborted sending of " + fileName);
        }
    }

    /**
     * The other user has accepted a file transfer. Will try to connect to the
     * user to send the file.
     *
     * @param userCode The unique code of the user who accepted a file transfer.
     * @param fileName The name of the file.
     * @param fileHash The hash code of the file.
     * @param port The port to use for connecting to the other user.
     */
    @Override
    public void fileSendAccepted(final int userCode, final String fileName, final int fileHash, final int port) {
        new Thread("DefaultMessageResponderFileSendAccepted") {
            @Override
            public void run() {
                final User user = controller.getUser(userCode);
                final FileSender fileSend = tList.getFileSender(user, fileName, fileHash);

                if (fileSend != null) {
                    msgController.showSystemMessage(user.getNick() + " accepted sending of " + fileName);

                    // Give the server some time to set up the connection first
                    Tools.sleep(200);

                    if (fileSend.transfer(port)) {
                        msgController.showSystemMessage(fileName + " successfully sent to " + user.getNick());
                    }

                    else {
                        msgController.showSystemMessage("Failed to send " + fileName + " to " + user.getNick());
                    }

                    tList.removeFileSender(fileSend);
                }
            }
        } .start();
    }

    /**
     * Updates the client information about the user.
     *
     * @param userCode The unique code of the user who sent client info.
     * @param client The client the user is using.
     * @param timeSinceLogon Number of milliseconds since the user logged on.
     * @param operatingSystem The user's operating system.
     * @param privateChatPort The port to use for sending private chat messages to this user.
     */
    @Override
    public void clientInfo(final int userCode, final String client, final long timeSinceLogon, final String operatingSystem, final int privateChatPort) {
        final User user = controller.getUser(userCode);

        if (user != null) {
            user.setClient(client);
            user.setLogonTime(System.currentTimeMillis() - timeSinceLogon);
            user.setOperatingSystem(operatingSystem);
            user.setPrivateChatPort(privateChatPort);
        }

        else {
            LOG.log(Level.SEVERE, "Could not find user: " + userCode);
        }
    }
}
