
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

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import net.usikkert.lanchat.Constants;
import net.usikkert.lanchat.misc.ErrorHandler;
import net.usikkert.lanchat.util.ResourceValidator;

/**
 * Loads, validates and gives access to all the images used in the application.
 *
 * <p>Note: if any of the images fails to load the application will exit.</p>
 *
 * @author Shouvik Goswami
 */
public class ImageLoader {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(ImageLoader.class.getName());

    /** The smile image icon. */
    private final ImageIcon smileIcon;

    /** The sad image icon. */
    private final ImageIcon sadIcon;

    /** The tongue image icon. */
    private final ImageIcon tongueIcon;

    /** The teeth image icon. */
    private final ImageIcon teethIcon;

    /** The wink image icon. */
    private final ImageIcon winkIcon;

    /** The omg image icon. */
    private final ImageIcon omgIcon;

    /** The angry image icon. */
    private final ImageIcon angryIcon;

    /** The confused image icon. */
    private final ImageIcon confusedIcon;

    /** The cry image icon. */
    private final ImageIcon cryIcon;

    /** The embarrassed image icon. */
    private final ImageIcon embarrassedIcon;

    /** The shade image icon. */
    private final ImageIcon shadeIcon;

    /** The normal kou image icon in 16x16px. */
    private final ImageIcon kouNormal16Icon;

    /** The normal kou image icon in 22x22px. */
    private final ImageIcon kouNormal22Icon;

    /** The normal kou image icon in 24x24px. */
    private final ImageIcon kouNormal24Icon;

    /** The normal kou image icon in 32x32px. */
    private final ImageIcon kouNormal32Icon;

    /** The normal activity kou image icon in 16x16px. */
    private final ImageIcon kouNormalActivity16Icon;

    /** The normal activity kou image icon in 22x22px. */
    private final ImageIcon kouNormalActivity22Icon;

    /** The normal activity kou image icon in 24x24px. */
    private final ImageIcon kouNormalActivity24Icon;

    /** The normal activity kou image icon in 32x32px. */
    private final ImageIcon kouNormalActivity32Icon;

    /** The away kou image icon in 16x16px. */
    private final ImageIcon kouAway16Icon;

    /** The away kou image icon in 22x22px. */
    private final ImageIcon kouAway22Icon;

    /** The away kou image icon in 24x24px. */
    private final ImageIcon kouAway24Icon;

    /** The away kou image icon in 32x32px. */
    private final ImageIcon kouAway32Icon;

    /** The away activity kou image icon in 16x16px. */
    private final ImageIcon kouAwayActivity16Icon;

    /** The away activity kou image icon in 22x22px. */
    private final ImageIcon kouAwayActivity22Icon;

    /** The away activity kou image icon in 24x24px. */
    private final ImageIcon kouAwayActivity24Icon;

    /** The away activity kou image icon in 32x32px. */
    private final ImageIcon kouAwayActivity32Icon;

    /** The envelope image icon. */
    private final ImageIcon envelopeIcon;

    /** The dot image icon. */
    private final ImageIcon dotIcon;

    /**
     * Constructor. Loads and validates the images.
     */
    public ImageLoader() {
        final ResourceValidator resourceValidator = new ResourceValidator();

        // Load resources from jar or local file system
        final URL smileURL = loadImage(resourceValidator, Images.SMILEY_SMILE);
        final URL sadURL = loadImage(resourceValidator, Images.SMILEY_SAD);
        final URL tongueURL = loadImage(resourceValidator, Images.SMILEY_TONGUE);
        final URL teethURL = loadImage(resourceValidator, Images.SMILEY_TEETH);
        final URL winkURL = loadImage(resourceValidator, Images.SMILEY_WINK);
        final URL omgURL = loadImage(resourceValidator, Images.SMILEY_OMG);
        final URL angryURL = loadImage(resourceValidator, Images.SMILEY_ANGRY);
        final URL confusedURL = loadImage(resourceValidator, Images.SMILEY_CONFUSED);
        final URL cryURL = loadImage(resourceValidator, Images.SMILEY_CRY);
        final URL embarrassedURL = loadImage(resourceValidator, Images.SMILEY_EMBARRASSED);
        final URL shadeURL = loadImage(resourceValidator, Images.SMILEY_SHADE);

        final URL kouNorm16URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_16);
        final URL kouNorm22URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_22);
        final URL kouNorm24URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_24);
        final URL kouNorm32URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_32);

        final URL kouNormAct16URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_ACT_16);
        final URL kouNormAct22URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_ACT_22);
        final URL kouNormAct24URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_ACT_24);
        final URL kouNormAct32URL = loadImage(resourceValidator, Images.ICON_KOU_NORMAL_ACT_32);

        final URL kouAway16URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_16);
        final URL kouAway22URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_22);
        final URL kouAway24URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_24);
        final URL kouAway32URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_32);

        final URL kouAwayAct16URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_ACT_16);
        final URL kouAwayAct22URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_ACT_22);
        final URL kouAwayAct24URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_ACT_24);
        final URL kouAwayAct32URL = loadImage(resourceValidator, Images.ICON_KOU_AWAY_ACT_32);

        final URL envelopeURL = loadImage(resourceValidator, Images.ICON_ENVELOPE);
        final URL dotURL = loadImage(resourceValidator, Images.ICON_DOT);

        validate(resourceValidator);

        // Create icons from the resources
        smileIcon = new ImageIcon(smileURL);
        sadIcon = new ImageIcon(sadURL);
        tongueIcon = new ImageIcon(tongueURL);
        teethIcon = new ImageIcon(teethURL);
        winkIcon = new ImageIcon(winkURL);
        omgIcon = new ImageIcon(omgURL);
        angryIcon = new ImageIcon(angryURL);
        confusedIcon = new ImageIcon(confusedURL);
        cryIcon = new ImageIcon(cryURL);
        embarrassedIcon = new ImageIcon(embarrassedURL);
        shadeIcon = new ImageIcon(shadeURL);

        kouNormal16Icon = new ImageIcon(kouNorm16URL);
        kouNormal22Icon = new ImageIcon(kouNorm22URL);
        kouNormal24Icon = new ImageIcon(kouNorm24URL);
        kouNormal32Icon = new ImageIcon(kouNorm32URL);

        kouNormalActivity16Icon = new ImageIcon(kouNormAct16URL);
        kouNormalActivity22Icon = new ImageIcon(kouNormAct22URL);
        kouNormalActivity24Icon = new ImageIcon(kouNormAct24URL);
        kouNormalActivity32Icon = new ImageIcon(kouNormAct32URL);

        kouAway16Icon = new ImageIcon(kouAway16URL);
        kouAway22Icon = new ImageIcon(kouAway22URL);
        kouAway24Icon = new ImageIcon(kouAway24URL);
        kouAway32Icon = new ImageIcon(kouAway32URL);

        kouAwayActivity16Icon = new ImageIcon(kouAwayAct16URL);
        kouAwayActivity22Icon = new ImageIcon(kouAwayAct22URL);
        kouAwayActivity24Icon = new ImageIcon(kouAwayAct24URL);
        kouAwayActivity32Icon = new ImageIcon(kouAwayAct32URL);

        envelopeIcon = new ImageIcon(envelopeURL);
        dotIcon = new ImageIcon(dotURL);
    }

    /**
     * Loads the image to a URL, and updates the validator with the result.
     * Either the image was loaded, or it was not.
     *
     * @param resourceValidator The validator.
     * @param image The image to load, with path.
     * @return The URL to the image, or <code>null</code> if the image wasn't loaded.
     */
    private URL loadImage(final ResourceValidator resourceValidator, final String image) {
        final URL url = getClass().getResource(image);
        resourceValidator.addResource(url, image);
        return url;
    }

    /**
     * Goes through all the images, and checks if they were loaded successfully.
     * If any of the images did not load successfully then a message is shown
     * to the user, and the application exits.
     *
     * @param resourceValidator The validator.
     */
    private void validate(final ResourceValidator resourceValidator) {
        final String missing = resourceValidator.validate();

        if (missing.length() > 0) {
            final String error = "These images were expected, but not found:\n\n" + missing + "\n\n" +
                    Constants.APP_NAME + " will now shutdown.";

            LOG.log(Level.SEVERE, error);
            ErrorHandler.getErrorHandler().showCriticalError(error);
            System.exit(1);
        }
    }

    /**
     * Gets the smileIcon.
     *
     * @return The smileIcon.
     */
    public ImageIcon getSmileIcon() {
        return smileIcon;
    }

    /**
     * Gets the sadIcon.
     *
     * @return The sadIcon.
     */
    public ImageIcon getSadIcon() {
        return sadIcon;
    }

    /**
     * Gets the tongueIcon.
     *
     * @return The tongueIcon.
     */
    public ImageIcon getTongueIcon() {
        return tongueIcon;
    }

    /**
     * Gets the teethIcon.
     *
     * @return The teethIcon.
     */
    public ImageIcon getTeethIcon() {
        return teethIcon;
    }

    /**
     * Gets the winkIcon.
     *
     * @return The winkIcon.
     */
    public ImageIcon getWinkIcon() {
        return winkIcon;
    }

    /**
     * Gets the omgIcon.
     *
     * @return The omgIcon.
     */
    public ImageIcon getOmgIcon() {
        return omgIcon;
    }

    /**
     * Gets the angryIcon.
     *
     * @return The angryIcon.
     */
    public ImageIcon getAngryIcon() {
        return angryIcon;
    }

    /**
     * Gets the confusedIcon.
     *
     * @return The confusedIcon.
     */
    public ImageIcon getConfusedIcon() {
        return confusedIcon;
    }

    /**
     * Gets the cryIcon.
     *
     * @return The cryIcon.
     */
    public ImageIcon getCryIcon() {
        return cryIcon;
    }

    /**
     * Gets the embarrassedIcon.
     *
     * @return The embarrassedIcon.
     */
    public ImageIcon getEmbarrassedIcon() {
        return embarrassedIcon;
    }

    /**
     * Gets the shadeIcon.
     *
     * @return The shadeIcon.
     */
    public ImageIcon getShadeIcon() {
        return shadeIcon;
    }

    /**
     * Gets the kouNormal16Icon.
     *
     * @return The kouNormal16Icon.
     */
    public ImageIcon getKouNormal16Icon() {
        return kouNormal16Icon;
    }

    /**
     * Gets the kouNormal22Icon.
     *
     * @return The kouNormal22Icon.
     */
    public ImageIcon getKouNormal22Icon() {
        return kouNormal22Icon;
    }

    /**
     * Gets the kouNormal24Icon.
     *
     * @return The kouNormal24Icon.
     */
    public ImageIcon getKouNormal24Icon() {
        return kouNormal24Icon;
    }

    /**
     * Gets the kouNormal32Icon.
     *
     * @return The kouNormal32Icon.
     */
    public ImageIcon getKouNormal32Icon() {
        return kouNormal32Icon;
    }

    /**
     * Gets the kouNormalActivity16Icon.
     *
     * @return The kouNormalActivity16Icon.
     */
    public ImageIcon getKouNormalActivity16Icon() {
        return kouNormalActivity16Icon;
    }

    /**
     * Gets the kouNormalActivity22Icon.
     *
     * @return The kouNormalActivity22Icon.
     */
    public ImageIcon getKouNormalActivity22Icon() {
        return kouNormalActivity22Icon;
    }

    /**
     * Gets the kouNormalActivity24Icon.
     *
     * @return The kouNormalActivity24Icon.
     */
    public ImageIcon getKouNormalActivity24Icon() {
        return kouNormalActivity24Icon;
    }

    /**
     * Gets the kouNormalActivity32Icon.
     *
     * @return The kouNormalActivity32Icon.
     */
    public ImageIcon getKouNormalActivity32Icon() {
        return kouNormalActivity32Icon;
    }

    /**
     * Gets the kouAway16Icon.
     *
     * @return The kouAway16Icon.
     */
    public ImageIcon getKouAway16Icon() {
        return kouAway16Icon;
    }

    /**
     * Gets the kouAway22Icon.
     *
     * @return The kouAway22Icon.
     */
    public ImageIcon getKouAway22Icon() {
        return kouAway22Icon;
    }

    /**
     * Gets the kouAway24Icon.
     *
     * @return The kouAway24Icon.
     */
    public ImageIcon getKouAway24Icon() {
        return kouAway24Icon;
    }

    /**
     * Gets the kouAway32Icon.
     *
     * @return The kouAway32Icon.
     */
    public ImageIcon getKouAway32Icon() {
        return kouAway32Icon;
    }

    /**
     * Gets the kouAwayActivity16Icon.
     *
     * @return The kouAwayActivity16Icon.
     */
    public ImageIcon getKouAwayActivity16Icon() {
        return kouAwayActivity16Icon;
    }

    /**
     * Gets the kouAwayActivity22Icon.
     *
     * @return The kouAwayActivity22Icon.
     */
    public ImageIcon getKouAwayActivity22Icon() {
        return kouAwayActivity22Icon;
    }

    /**
     * Gets the kouAwayActivity24Icon.
     *
     * @return The kouAwayActivity24Icon.
     */
    public ImageIcon getKouAwayActivity24Icon() {
        return kouAwayActivity24Icon;
    }

    /**
     * Gets the kouAwayActivity32Icon.
     *
     * @return The kouAwayActivity32Icon.
     */
    public ImageIcon getKouAwayActivity32Icon() {
        return kouAwayActivity32Icon;
    }

    /**
     * Gets the envelopeIcon.
     *
     * @return The envelopeIcon.
     */
    public ImageIcon getEnvelopeIcon() {
        return envelopeIcon;
    }

    /**
     * Gets the dotIcon.
     *
     * @return The dotIcon.
     */
    public ImageIcon getDotIcon() {
        return dotIcon;
    }
}
