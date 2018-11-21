////////////////////////////////////////////////////////////////////////////////
// BlueJ Checkstyle extension:
//    Checks Java source code for adherence to a set of rules.
// Copyright (C) 2003-2004  Rick Giles
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

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
 * @version $Id: Preferences.java,v 1.5 2007/08/19 03:13:52 stedwar2 Exp $
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


