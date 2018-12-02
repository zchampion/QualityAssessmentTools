package com.SoftwareExtensionRenovators.bluejmanager;

import com.SoftwareExtensionRenovators.toolbox.pmd.PMDAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

import bluej.extensions.*;

/**
 * Manages the Quality Assessment Tools extension menu items
 * @author Rick Giles 
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version 0.6.1
 */
public class ExtensionMenu extends MenuGenerator {
    /**
     * @see bluej.extensions.MenuGenerator#getToolsMenuItem(bluej.extensions.BPackage)
     */
    public JMenuItem getToolsMenuItem(BPackage aPackage) {

        final JMenu toolsMenu = new JMenu("Quality Assessment Tools");

        final JMenuItem checkstyleMenu = new JMenuItem("Checkstyle");
        checkstyleMenu.addActionListener(new MenuAction());

        final JMenuItem pmdMenu = new JMenuItem("PMD");
        pmdMenu.addActionListener(new PMDAction("PMD",aPackage));

        final JMenuItem spotBugsMunu = new JMenuItem("SpotBugs");
        spotBugsMunu.setEnabled(false);

        toolsMenu.add(checkstyleMenu);
        toolsMenu.add(pmdMenu);
        toolsMenu.add(spotBugsMunu);


        return toolsMenu;

    }
    /**
     * @see bluej.extensions.MenuGenerator#getMenuItem()
     * @deprecated
     * Deprecated as of BlueJ 1.3.5.
     * Added for compatibility with BlueJ 1.3.0.
     */
    public JMenuItem getMenuItem() {
        final JMenuItem item = new JMenuItem("Checkstyle");
        item.addActionListener(new MenuAction());
        return item;
    }

    /**
     * Action listener for the Checkstyle menu item.
     * Audits files of the current package.
     * @author Rick Giles
     * @version 13-May-2003
     */
    class MenuAction implements ActionListener
    {
        /**
         * Audits the open projects and shows the results.
         * @see java.awt.event.ActionListener
         */
        public void actionPerformed(ActionEvent aEvent)
        {
            QualityToolExtension.getInstance().mCheckstyleUI.showAuditFrame();
        }
    }


}
