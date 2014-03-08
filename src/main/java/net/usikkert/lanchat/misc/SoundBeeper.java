
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

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.usikkert.lanchat.util.Validate;

/**
 * Can load an audio file, and play it.
 *
 * @author Shouvik Goswami
 */
public class SoundBeeper {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(SoundBeeper.class.getName());

    /**
     * The file to play when beep() is run.
     */
    private static final String BEEP_FILE = "/sounds/error.wav";

    /**
     * The number of milliseconds to wait after
     * beeping before closing.
     */
    private static final int WAIT_PERIOD = 5000;

    private final Settings settings;
    private final ErrorHandler errorHandler;
    private Clip audioClip;
    private Thread closeTimer;
    private long closeTime;

    /**
     * Default constructor.
     *
     * @param settings The settings to use.
     */
    public SoundBeeper(final Settings settings) {
        Validate.notNull(settings, "Settings can not be null");

        this.settings = settings;
        errorHandler = ErrorHandler.getErrorHandler();
    }

    /**
     * Plays the loaded audio file if sound is enabled, and
     * it's not already playing. If nothing has been played for
     * 5 seconds the sound resource is released.
     */
    public synchronized void beep() {
        if (settings.isSound()) {
            if (audioClip == null || !audioClip.isActive()) {
                if (audioClip == null) {
                    open();
                } else {
                    audioClip.setFramePosition(0);
                }

                if (audioClip != null) {
                    audioClip.start();
                    closeTime = System.currentTimeMillis() + WAIT_PERIOD;

                    if (closeTimer == null) {
                        closeTimer = new Thread(new CloseTimer(), "SoundBeeperCloseTimer");
                        closeTimer.start();
                    }
                }

                else {
                    LOG.log(Level.SEVERE, "Audio clip missing.");
                }
            }
        }
    }

    /**
     * Opens an audio file, and reserves the resources needed for playback.
     */
    public void open() {
        final URL fileUrl = getClass().getResource(BEEP_FILE);

        if (fileUrl != null) {
            AudioInputStream audioStream = null;

            try {
                audioStream = AudioSystem.getAudioInputStream(fileUrl);
                final AudioFormat format = audioStream.getFormat();
                final DataLine.Info info = new DataLine.Info(Clip.class, format);

                if (AudioSystem.isLineSupported(info)) {
                    audioClip = (Clip) AudioSystem.getLine(info);
                    audioClip.open(audioStream);
                }
            }

            catch (final UnsupportedAudioFileException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
                settings.setSound(false);
                errorHandler.showError("Could not initialize the sound." +
                        "\nUnsupported file format: " + BEEP_FILE);
            }

            catch (final IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
                settings.setSound(false);
                errorHandler.showError("Could not initialize the sound." +
                        "\nAudio file could not be opened: " + BEEP_FILE);
            }

            catch (final LineUnavailableException e) {
                LOG.log(Level.WARNING, e.toString(), e);
            }

            finally {
                if (audioStream != null) {
                    try {
                        audioStream.close();
                    }

                    catch (final IOException e) {
                        LOG.log(Level.WARNING, e.toString());
                    }
                }
            }
        }

        else {
            LOG.log(Level.SEVERE, "Audio file not found: " + BEEP_FILE);
            settings.setSound(false);
            errorHandler.showError("Could not initialize the sound." +
                    "\nAudio file not found: " + BEEP_FILE);
        }
    }

    /**
     * Closes the audio file and frees the resources used.
     */
    public void close() {
        if (audioClip != null) {
            audioClip.flush();
            audioClip.close();
            audioClip = null;
        }
    }

    /**
     * A simple thread used for freeing sound resources when finished.
     *
     * @author Christian Ihle
     */
    private class CloseTimer implements Runnable {
        /** The method that runs when the thread starts. */
        @Override
        public void run() {
            while (System.currentTimeMillis() < closeTime) {
                try {
                    Thread.sleep(1000);
                }

                catch (final InterruptedException e) {
                    LOG.log(Level.WARNING, e.toString());
                }
            }

            close();
            closeTimer = null;
        }
    }
}
