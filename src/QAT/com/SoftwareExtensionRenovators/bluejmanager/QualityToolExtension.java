package com.SoftwareExtensionRenovators.bluejmanager;

import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import com.SoftwareExtensionRenovators.toolbox.checkstyle.CheckstyleUI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;

/**
 * BlueJ extension for Quality Assessment Tools.
 * @author Rick Giles
 * @author CS4250 Students (MSU Denver)
 * @author Rick Giles 
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version 0.6.1
 */
public class QualityToolExtension extends Extension
{
    /** true if the log factory has been initialized */
    public static boolean mLogInitialized = false;

    /** Factory for creating org.apache.commons.logging.Log instances */
    public static LogFactory mLogFactory;

    /** singleton */
    private static QualityToolExtension sInstance;

    /** BlueJ tools menu item for Quality Tool Extension */
    private ExtensionMenu mMenu;

    /** extension name */
    private static final String NAME = "Quality Assessment Tools";

    /** extension description */
    private static final String DESCRIPTION =
            "Various tools used to analyze a user's Java source code.";

    /**  extension version */
    private static final String VERSION = "0.6.1";

    /** extension URL */
    private static final String URL =
            "https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/";

    /** Handles display and events for checkstyle tool */
    public com.SoftwareExtensionRenovators.toolbox.checkstyle.CheckstyleUI mCheckstyleUI;

    /**
     * Returns the single QualityAssessmentExtension instance.
     * @return  the single QualityAssessmentExtension instance.
     */
    public static QualityToolExtension getInstance()
    {
        return sInstance;
    }

    /**
     * Constructs a <code>QualityAssessmentExtension</code>.
     */
    public QualityToolExtension()
    {
        sInstance = this; // establish singleton extension

        try {
            mLogFactory = LogFactory.getFactory();
            mLogInitialized = true;
        }
        catch (LogConfigurationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }


    /** @see bluej.extensions.Extension#startup(bluej.extensions.BlueJ) */
    public void startup(BlueJ aBlueJ)
    {
        // establish singleton manager for the BlueJ application proxy
        BlueJManager.getInstance().setBlueJ(aBlueJ);

        mCheckstyleUI = CheckstyleUI.getCheckstyleUI();
        mCheckstyleUI.addListeners(aBlueJ);

        // install menu item
        mMenu = new ExtensionMenu();
        aBlueJ.setMenuGenerator(mMenu);

        // install preferences handler
        Preferences myPreferences = new Preferences();
        aBlueJ.setPreferenceGenerator(myPreferences);
    }

    /**
     * Saves audit window information.
     * @see bluej.extensions.Extension#terminate()
     */
    public void terminate()
    {
        mCheckstyleUI.terminate();
    }

    /** @see bluej.extensions.Extension#getDescription() */
    public String getDescription()
    {
        return DESCRIPTION;
    }

    /** @see bluej.extensions.Extension#getName() */
    public String getName()
    {
        return NAME;
    }

    /** @see bluej.extensions.Extension#getURL() */
    public URL getURL()
    {
        URL result = null;
        try {
            result = new URL(URL);
        }
        catch (MalformedURLException e) {
            error(e);
        }
        return result;
    }

    /** @see bluej.extensions.Extension#getVersion() */
    public String getVersion()
    {
        return VERSION;
    }

    /** @see bluej.extensions.Extension#isCompatible() */
    public boolean isCompatible()
    {
        return true;
    }

    /**
     * Reports an error message.
     * @param aMessage the message to report.
     */
    public static void error(String aMessage)
    {
        Frame frame = BlueJManager.getInstance().getCurrentFrame();
        JOptionPane.showMessageDialog(frame, aMessage);
        if (mLogInitialized) {
            final Log log = mLogFactory.getInstance(QualityToolExtension.class);
            log.info(aMessage);
        }
        else {
            System.out.println(aMessage);
        }
    }

    /**
     * Reports an exception.
     * @param aException the exception to report.
     */
    public static void error(Exception aException)
    {
        aException.printStackTrace();
        error("" + aException);
    }
}
