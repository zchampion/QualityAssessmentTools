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

package com.SoftwareExtensionRenovators.toolbox.checkstyle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


import javax.swing.SwingUtilities;
import javax.swing.Timer;

import bluej.extensions.BlueJ;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;
import bluej.extensions.event.CompileEvent;
import bluej.extensions.event.CompileListener;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;
import com.SoftwareExtensionRenovators.bluejmanager.BlueJManager;
import com.SoftwareExtensionRenovators.bluejmanager.QualityToolExtension;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
/**
 * Manages UI elements of checkstyle extension. Refactored code.
 * @author Rick Giles
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version $Id: Preferences.java,v 1.5 2007/08/19 03:13:52 stedwar2 Exp $
 */
public class CheckstyleUI
{
    /** Singleton instance. */
    private static CheckstyleUI mInstance;

    /** Display audit results. */
    private com.SoftwareExtensionRenovators.toolbox.checkstyle.AuditFrame mFrame = null;

    /** Periodically checks for file set changes. */
    private Timer mTimer;

    /** Files being compiled. */
    private Set<File> mCompilingFiles = new HashSet<File>();

    /** Interval between audit checks (milliseconds). */
    private static final int AUDIT_CHECK_INTERVAL = 2000;

    /** audit window dimensions */
    private static final String FRAME_DIMENSIONS_KEY = "framedimensions";

    /** offset of corner relative to current frame */
    private static final int FRAME_OFFSET = 20;

    /** determine whether checkstyle audit window is open */
    private static final String IS_OPEN_KEY = "frameisopen";

    /** Private constructor for singleton. */
    private CheckstyleUI(){
        final ActionListener listener = new FilesChangeListener();
        mTimer = new Timer(AUDIT_CHECK_INTERVAL, listener);
        mTimer.start();
    }


    /**
     * Public method to access singleton
     * @return mInstance instance of CheckstyleUI class
     */
    public static CheckstyleUI getCheckstyleUI(){
        if (mInstance == null){
            mInstance = new CheckstyleUI();
            return  mInstance;
        }
        else{
            return mInstance;
        }
    }

    /**
     *
     * Registers a listener for application events, package events, and compile events.
     * @param aBlueJ A proxy object which provides services to BlueJ extensions.
     */
    public void addListeners(BlueJ aBlueJ){
        // register listeners
        aBlueJ.addApplicationListener(new CheckstyleApplicationListener());
        aBlueJ.addPackageListener(new CheckstylePackageListener());
        aBlueJ.addCompileListener(new CheckstyleCompileListener());
    }

    /**
     * Saves audit window information.
     * @see bluej.extensions.Extension#terminate()
     */
    public void terminate()
    {
        saveAuditFrame(mFrame);
        mCompilingFiles.clear();
        mTimer.stop();
    }

    /**
     * Creates and installs an audit frame
     */
    private synchronized void buildAuditFrame()
    {
        /** @see java.awt.event.WindowAdapter */
        final class AuditFrameListener extends WindowAdapter
        {
            /** @see java.awt.event.WindowListener */
            public void windowOpened(WindowEvent aEvent)
            {
                updateTimer();
            }

            /** @see java.awt.event.WindowListener */
            public void windowClosed(WindowEvent aEvent)
            {
                updateTimer();
            }

            /** @see java.awt.event.WindowListener */
            public void windowIconified(WindowEvent aEvent)
            {
                updateTimer();
            }

            /** @see java.awt.event.WindowListener */
            public void windowDeiconified(WindowEvent aEvent)
            {
                updateTimer();
            }
        }

        if (mFrame == null) {
            mFrame = new com.SoftwareExtensionRenovators.toolbox.checkstyle.AuditFrame();
            mFrame.addWindowListener(new AuditFrameListener());
            initAuditFrame(mFrame);
            mFrame.pack();
        }
    }

    /**
     * Initializes an audit frame from extension properties.
     * @param aFrame the audit frame to initialize.
     */
    public void initAuditFrame(com.SoftwareExtensionRenovators.toolbox.checkstyle.AuditFrame aFrame)
    {
        BlueJManager manager = BlueJManager.getInstance();

        // location and size
        final String frameDimensions =
                manager.getExtensionPropertyString(FRAME_DIMENSIONS_KEY, "");
        if (!frameDimensions.equals(""))
        {
            final StringTokenizer tokenizer =
                    new StringTokenizer(frameDimensions);
            final int x = Integer.parseInt(tokenizer.nextToken());
            final int y = Integer.parseInt(tokenizer.nextToken());
            aFrame.setLocation(x, y);
            // aFrame.setSize(width, height);
        }
        else
        {
            final Frame bluejFrame = manager.getCurrentFrame();
            Point corner = new Point(0, 0);
            if (bluejFrame != null) {
                corner = bluejFrame.getLocation();
            }
            corner.translate(FRAME_OFFSET, FRAME_OFFSET);
            aFrame.setLocation(corner);
        }
        if (Boolean.valueOf(manager.getExtensionPropertyString(
                IS_OPEN_KEY, "false")).booleanValue())
        {
            aFrame.setVisible(true);
        }
    }

    /**
     * Saves audit frame information in properties.
     * @param aFrame the frame to save.
     */
    public void saveAuditFrame(AuditFrame aFrame)
    {
        BlueJManager manager = BlueJManager.getInstance();

        manager.setExtensionPropertyString(
                IS_OPEN_KEY, "" + aFrame.isShowing());
        final String frameDimensions = (int) aFrame.getLocation().getX() + " "
                + (int) aFrame.getLocation().getY() + " "
                + (int) aFrame.getSize().getWidth() + " "
                + (int) aFrame.getSize().getHeight();
        manager.setExtensionPropertyString(FRAME_DIMENSIONS_KEY, frameDimensions);
    }

    /**
     * Refreshes the audit view. If there is an error, report it.
     */
    public void refreshView()
    {
        if (mFrame.isShowing()) {
            final com.SoftwareExtensionRenovators.toolbox.checkstyle.Auditor auditor;
            try {
                auditor = BlueJChecker.processAllFiles();
            }
            catch (CheckstyleException ex) {
                QualityToolExtension.error(ex);
                return;
            }
            viewAudit(auditor);
        }
    }

    /**
     * Shows the audit frame.
     */
    public void showAuditFrame()
    {
        buildAuditFrame();
        mFrame.setVisible(true);
        refreshView();
    }

    /**
     * Updates view of audit results.
     * @param aAuditor the auditor with audit results
     */
    public synchronized void viewAudit(final Auditor aAuditor)
    {
        // execute on the application's event-dispatch thread
        final Runnable update = new Runnable()
        {
            public void run()
            {
                if (mFrame != null) {
                    mFrame.setAuditor(aAuditor);
                }
            }
        };
        SwingUtilities.invokeLater(update);
    }


    /**
     * Starts or stops timer.
     */
    private void updateTimer()
    {
        if (mCompilingFiles.isEmpty() && mFrame.isShowing()) {
            mTimer.start();
        }
        else {
            mTimer.stop();
        }
    }

    /** @see bluej.extensions.event.PackageListener */
    private class CheckstylePackageListener implements PackageListener
    {
        /** @see bluej.extensions.event.PackageListener */
        public void packageOpened(PackageEvent aEvent)
        {
            // refreshView();
        }

        /** @see bluej.extensions.event.PackageListener */
        public void packageClosing(PackageEvent aEvent)
        {
            // refreshView();
        }
    }

    /** @see bluej.extensions.event.CompileListener */
    private class CheckstyleCompileListener implements CompileListener
    {
        /** @see bluej.extensions.event.CompileListener */
        public void compileStarted(CompileEvent aEvent)
        {
            recordCompileStart(aEvent.getFiles());
        }

        /**
         * Records the start of compilation of a set of files.
         * @param aFiles the set of files being compiled.
         */
        private void recordCompileStart(File[] aFiles)
        {
            for (int i = 0; i < aFiles.length; i++) {
                mCompilingFiles.add(aFiles[i]);
            }
            updateTimer();
        }

        /** @see bluej.extensions.event.CompileListener */
        public void compileSucceeded(CompileEvent aEvent)
        {
            recordCompileEnd(aEvent.getFiles());
            if (mCompilingFiles.isEmpty()) {
                refreshView();
            }
        }

        /** @see bluej.extensions.event.CompileListener */
        public void compileError(CompileEvent aEvent)
        {
            recordCompileEnd(aEvent.getFiles());
        }

        /** @see bluej.extensions.event.CompileListener */
        public void compileWarning(CompileEvent aEvent)
        {
            recordCompileEnd(aEvent.getFiles());
        }

        /** @see bluej.extensions.event.CompileListener */
        public void compileFailed(CompileEvent aEvent)
        {
            recordCompileEnd(aEvent.getFiles());
        }

        /**
         * Records the end of compilation of a set of files.
         * @param aFiles the set of files ending compilation.
         */
        private void recordCompileEnd(File[] aFiles)
        {
            for (int i = 0; i < aFiles.length; i++) {
                mCompilingFiles.remove(aFiles[i]);
            }
            updateTimer();
        }
    }

    /** @see bluej.extensions.event.ApplicationListener */
    private class CheckstyleApplicationListener implements ApplicationListener
    {

        /**
         * Initializes the audit window.
         * @see bluej.extensions.event.ApplicationListener
         */
        public void blueJReady(ApplicationEvent aEvent)
        {
            buildAuditFrame();
            refreshView();
        }
    }
}
