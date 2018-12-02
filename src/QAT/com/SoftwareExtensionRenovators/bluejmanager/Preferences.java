package com.SoftwareExtensionRenovators.bluejmanager;

import javax.swing.*;

import bluej.extensions.BlueJ;
import bluej.extensions.PreferenceGenerator;
import com.SoftwareExtensionRenovators.toolbox.pmd.SystemUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Manages Checkstyle extension panel in BlueJ preferences.
  * @author Rick Giles 
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version 0.6.1
 */
public class Preferences implements PreferenceGenerator
{
    private com.SoftwareExtensionRenovators.toolbox.checkstyle.Preferences mCheckstylePref;
    private com.SoftwareExtensionRenovators.toolbox.pmd.Preferences mPMDPref;


    public Preferences()
    {
        mCheckstylePref = new com.SoftwareExtensionRenovators.toolbox.checkstyle.Preferences();
        mPMDPref = new com.SoftwareExtensionRenovators.toolbox.pmd.Preferences();

    }

    /** @see bluej.extensions.PreferenceGenerator#saveValues() */
    public void saveValues()
    {

        mCheckstylePref.saveValues();
        mPMDPref.saveValues();
    }

    /** @see bluej.extensions.PreferenceGenerator#loadValues() */
    public void loadValues()
    {

        mCheckstylePref.loadValues();
        mPMDPref.loadValues();
    }

    /** @see bluej.extensions.PreferenceGenerator#getPanel() */
    public JPanel getPanel()
    {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(mPMDPref.getPanel());
        container.add(mCheckstylePref.getPanel());

        return container;

    }
}


