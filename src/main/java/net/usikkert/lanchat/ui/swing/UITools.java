
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

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.misc.ErrorHandler;
import net.usikkert.lanchat.misc.Settings;
import net.usikkert.lanchat.util.Validate;

/**
 * This is a collection of practical and reusable methods
 * for ui use.
 *
 * @author Christian Ihle
 */
public final class UITools {

    private static final Logger LOG = Logger.getLogger(UITools.class.getName());
    private static final ErrorHandler ERRORHANDLER = ErrorHandler.getErrorHandler();

    /**
     * Private constructor. Only static methods here.
     */
    private UITools() {

    }

    /**
     * Opens a url in a browser. The first choice is taken from the settings,
     * but if no browser i configured there, the systems default browser
     * is tried.
     *
     * @param url The url to open in the browser.
     * @param settings The settings to use.
     */
    public static void browse(final String url, final Settings settings) {
        Validate.notEmpty(url, "Url can not be empty");
        Validate.notNull(settings, "Settings can not be null");

        final String browser = settings.getBrowser();

        // The default is to use the browser in the settings.
        if (browser != null && browser.trim().length() > 0) {
            try {
                Runtime.getRuntime().exec(browser + " " + url);
            }

            catch (final IOException e) {
                LOG.log(Level.WARNING, e.toString());
                ERRORHANDLER.showError("Could not open the browser '" +
                        browser + "'. Please check the settings.");
            }
        }

        // But if no browser is set there, try opening the system default browser
        else if (isDesktopActionSupported(Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            }

            catch (final IOException e) {
                LOG.log(Level.WARNING, e.toString());
                ERRORHANDLER.showError("Could not open '" + url + "' with the default browser." +
                        " Try setting a browser in the settings.");
            }

            catch (final URISyntaxException e) {
                LOG.log(Level.WARNING, e.toString());
            }
        }

        else {
            ERRORHANDLER.showError("No browser detected." +
                    " A browser can be chosen in the settings.");
        }
    }

    /**
     * Opens a file in the registered application for the file type.
     *
     * <p>If this fails, {@link #browse(String, Settings)} is used as a fallback.</p>
     *
     * @param file A file or directory to open.
     * @param settings The settings to use.
     */
    public static void open(final File file, final Settings settings) {
        Validate.notNull(file, "File can not be null");
        Validate.notNull(settings, "Settings can not be null");

        boolean desktopOpenSuccess = false;

        if (isDesktopActionSupported(Action.OPEN)) {
            try {
                Desktop.getDesktop().open(file);
                desktopOpenSuccess = true;
            }

            catch (final IOException e) {
                LOG.log(Level.WARNING, e.toString());
            }
        }

        if (!desktopOpenSuccess) {
            browse(file.getAbsolutePath(), settings);
        }
    }

    /**
     * Checks if the desktop api is supported in this system,
     * and if that is the case, then a check to see whether the
     * chosen desktop action is supported on this system is performed.
     *
     * <p>The reason to do the checks so thorough is because an
     * unchecked exception is thrown when {@link Desktop#getDesktop()}
     * is called on an unsupported system.</p>
     *
     * @param action The action to check.
     * @return If the system supports this action or not.
     */
    public static boolean isDesktopActionSupported(final Action action) {
        if (Desktop.isDesktopSupported()) {
            if (Desktop.getDesktop().isSupported(action)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Changes to the system Look And Feel.
     * Ignores any exceptions, as this is not critical.
     */
    public static void setSystemLookAndFeel() {
        if (isSystemLookAndFeelSupported()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }

            catch (final ClassNotFoundException e) {
                LOG.log(Level.WARNING, e.toString());
            }

            catch (final InstantiationException e) {
                LOG.log(Level.WARNING, e.toString());
            }

            catch (final IllegalAccessException e) {
                LOG.log(Level.WARNING, e.toString());
            }

            catch (final UnsupportedLookAndFeelException e) {
                LOG.log(Level.WARNING, e.toString());
            }
        }
    }

    /**
     * Changes to the chosen look and feel. Ignores any exceptions.
     *
     * @param lnfName Name of the look and feel to change to.
     */
    public static void setLookAndFeel(final String lnfName) {
        try {
            final LookAndFeelInfo lookAndFeel = getLookAndFeel(lnfName);

            if (lookAndFeel != null) {
                UIManager.setLookAndFeel(lookAndFeel.getClassName());
            }
        }

        catch (final ClassNotFoundException e) {
            LOG.log(Level.WARNING, e.toString());
        }

        catch (final InstantiationException e) {
            LOG.log(Level.WARNING, e.toString());
        }

        catch (final IllegalAccessException e) {
            LOG.log(Level.WARNING, e.toString());
        }

        catch (final UnsupportedLookAndFeelException e) {
            LOG.log(Level.WARNING, e.toString());
        }
    }

    /**
     * Checks if the system look and feel differs
     * from the cross platform look and feel.
     *
     * @return True if the system look and feel is different
     * from the cross platform look and feel.
     */
    public static boolean isSystemLookAndFeelSupported() {
        return !UIManager.getSystemLookAndFeelClassName().equals(UIManager.getCrossPlatformLookAndFeelClassName());
    }

    /**
     * Gets an array of the available look and feels, in a wrapper.
     *
     * @return All the available look and feels.
     */
    public static LookAndFeelWrapper[] getLookAndFeels() {
        final LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        final LookAndFeelWrapper[] lookAndFeelWrappers = new LookAndFeelWrapper[lookAndFeels.length];

        for (int i = 0; i < lookAndFeels.length; i++) {
            lookAndFeelWrappers[i] = new LookAndFeelWrapper(lookAndFeels[i]);
        }

        return lookAndFeelWrappers;
    }

    /**
     * Gets the {@link LookAndFeelInfo} found with the specified name,
     * or null if none was found.
     *
     * @param lnfName The name of the look and feel to look for.
     * @return The LookAndFeelInfo for that name.
     */
    public static LookAndFeelInfo getLookAndFeel(final String lnfName) {
        final LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();

        for (final LookAndFeelInfo lookAndFeelInfo : lookAndFeels) {
            if (lookAndFeelInfo.getName().equals(lnfName)) {
                return lookAndFeelInfo;
            }
        }

        return null;
    }

    /**
     * Gets {@link LookAndFeelInfo} for the current look and feel.
     *
     * @return The current look and feel, or <code>null</code> if none is set.
     */
    public static LookAndFeelInfo getCurrentLookAndFeel() {
        final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();

        if (lookAndFeel == null) {
            return null;
        }

        final LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();

        for (final LookAndFeelInfo lookAndFeelInfo : lookAndFeels) {
            if (lookAndFeelInfo.getClassName().equals(lookAndFeel.getClass().getName())) {
                return lookAndFeelInfo;
            }
        }

        return null;
    }

    /**
     * Gets the width of the text in pixels with the specified font.
     *
     * @param text The text to check the width of.
     * @param graphics Needed to be able to check the width.
     * @param font The font the text uses.
     * @return The text width, in pixels.
     */
    public static double getTextWidth(final String text, final Graphics graphics, final Font font) {
        final FontMetrics fm = graphics.getFontMetrics(font);
        return fm.getStringBounds(text, graphics).getWidth();
    }

    /**
     * Shows an information message dialog with the specified message and title.
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     */
    public static void showInfoMessage(final String message, final String title) {
        showMessageDialog(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a warning message dialog with the specified message and title.
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     */
    public static void showWarningMessage(final String message, final String title) {
        showMessageDialog(message, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows an error message dialog with the specified message and title.
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     */
    public static void showErrorMessage(final String message, final String title) {
        showMessageDialog(message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a message dialog with the specified message, title and type.
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     * @param messageType The type of message. See {@link JOptionPane} for types.
     */
    public static void showMessageDialog(final String message, final String title, final int messageType) {
        JOptionPane.showMessageDialog(null, message, createTitle(title), messageType);
    }

    /**
     * Shows an input dialog with the specified message, title and initial value
     * in the input field.
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     * @param initialValue The initial value, or <code>null</code> if the field should be empty.
     * @return The input from the user, or <code>null</code> if cancel was selected.
     */
    public static String showInputDialog(final String message, final String title, final String initialValue) {
        return (String) JOptionPane.showInputDialog(null, message, createTitle(title),
                JOptionPane.QUESTION_MESSAGE, null, null, initialValue);
    }

    /**
     * Shows an option dialog with the specified message and title,
     * with the buttons set to "Yes" and "Cancel".
     *
     * @param message The message to show.
     * @param title The title of the dialog box.
     * @return Which button the user pressed. See {@link JOptionPane} for options.
     */
    public static int showOptionDialog(final String message, final String title) {
        final Object[] options = {"Yes", "Cancel"};
        final int[] choice = new int[1];

        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                choice[0] = JOptionPane.showOptionDialog(null, message, createTitle(title),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
            }
        });

        return choice[0];
    }

    /**
     * Creates a new title by appending a dash and the application name
     * after the original title.
     *
     * @param title The original title.
     * @return The new title.
     */
    public static String createTitle(final String title) {
        return title + " - " + Constants.APP_NAME;
    }

    /**
     * Creates a new file chooser with the specified title.
     *
     * @param title The title of the file chooser.
     * @return A new file chooser.
     */
    public static JFileChooser createFileChooser(final String title) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(createTitle(title));
        return fileChooser;
    }

    /**
     * Shows the color chooser with the chosen title, with the initial color.
     *
     * @param title The title of the color chooser.
     * @param initialColor The initial color to use in the color chooser.
     * @return The selected color.
     */
    public static Color showColorChooser(final String title, final Color initialColor) {
        return JColorChooser.showDialog(null, createTitle(title), initialColor);
    }

    /**
     * Checks if a window is minimized to the taskbar.
     *
     * @param frame The window to check.
     * @return If the window is minimized.
     */
    public static boolean isMinimized(final JFrame frame) {
        return (frame.getExtendedState() & JFrame.ICONIFIED) != 0;
    }

    /**
     * Restores a minimized window so it's visible again.
     *
     * @param frame The window to restore.
     */
    public static void restore(final JFrame frame) {
        if (isMinimized(frame)) {
            frame.setExtendedState(frame.getExtendedState() & ~JFrame.ICONIFIED);
        }
    }

    /**
     * Minimizes a window to the taskbar.
     *
     * @param frame The window to minimize.
     */
    public static void minimize(final JFrame frame) {
        if (!isMinimized(frame)) {
            frame.setExtendedState(frame.getExtendedState() | JFrame.ICONIFIED);
        }
    }

    /**
     * A wrapper for {@link SwingUtilities#invokeAndWait(Runnable)}, that catches checked exceptions,
     * and rethrows them as unchecked, but only if necessary. Runs the runnable directly if already on
     * the EDT.
     *
     * @param runnable The runnable to invoke and wait for.
     */
    public static void invokeAndWait(final Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        }

        else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checks if the application is running on a KDE desktop. This is detected by the presence of
     * the environment variable <code>KDE_FULL_SESSION</code>.
     *
     * @return If running on KDE.
     */
    public static boolean isRunningOnKDE() {
        return System.getenv("KDE_FULL_SESSION") != null;
    }
}
