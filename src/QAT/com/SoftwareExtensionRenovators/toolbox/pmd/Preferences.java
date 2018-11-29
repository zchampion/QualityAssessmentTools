package com.SoftwareExtensionRenovators.toolbox.pmd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bluej.extensions.BlueJ;
import bluej.extensions.PreferenceGenerator;
import com.SoftwareExtensionRenovators.bluejmanager.BlueJManager;


/**
 * Manages PMD extension panel in BlueJ preferences.
 * @author Ted Mittelstaedt, from an original by Tom Copeland
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version 0.0.1
 */
public class Preferences implements PreferenceGenerator {

    private JPanel panel;
    private JTextField pmdPath;
    private JTextField pmdOptions;
    public static final String PROPERTY_PMD_PATH = "PMD.Path";
    public static final String PROPERTY_PMD_OPTIONS = "PMD.Options";
    private static final String PMD_OPTIONS_DEFAULT = ",-f,text,-R,java-quickstart,-version,1.8,-language,java,-no-cache,-d,";


    /**
     * Creates a <code>Preferences</code> object that manages
     * the PMD extension panel of the BlueJ Preferences dialog.
     * Panel allows user to select a path to the PMD folder located
     * on their computer as well as change the default PMD options and rulesets.
     */
    public Preferences() {

        panel = new JPanel();
        pmdPath = new JTextField();
        pmdOptions = new JTextField();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Path to PMD installation:"), c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(pmdPath,c );

        JButton selectPmdPathButton = new JButton("Select");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(selectPmdPathButton, c);

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("PMD Options:"), c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(pmdOptions, c);

        JButton resetToDefaultButton = new JButton("Reset to default");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(resetToDefaultButton, c);


        selectPmdPathButton.addActionListener(new ActionListener() {

            /**
             * Allows user to select path to executables of PMD
             * located locally
             * @param e instance of ActionEvent class
             */
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File(pmdPath.getText()));
                int result = fileChooser.showDialog(panel, "Select");
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    boolean valid = verifyPMDPath(selectedFile);
                    if (valid) {
                        pmdPath.setText(selectedFile.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(panel, "The selected path " + selectedFile + " doesn't seem to be"
                                + " a PMD installation. E.g. the file bin/pmd.bat or bin/run.sh is missing.");
                    }
                }
            }
        });

        resetToDefaultButton.addActionListener(new ActionListener() {
            /**
             * Sets the Preference panel to a default string command
             * @param e instance of ActionEvent class
             */
            public void actionPerformed(ActionEvent e) {
                pmdOptions.setText(PMD_OPTIONS_DEFAULT);
            }
        });

        loadValues();
    }

    /**
     * Verifies that user entered path contains PMD executables
     * @param selectedFile  directory path to PMD executables located locally
     * @return boolean  valid executables present
     */
    private boolean verifyPMDPath(File selectedFile) {
        File pathToExecutable;
        if (SystemUtils.isWindows()) {
            pathToExecutable = new File(selectedFile, "bin/pmd.bat");
        } else {
            pathToExecutable = new File(selectedFile, "bin/run.sh");
        }
        return pathToExecutable.exists();
    }
    /** @see bluej.extensions.PreferenceGenerator#getPanel() */
    public JPanel getPanel ()  { return panel; }

    /** @see bluej.extensions.PreferenceGenerator#saveValues() */
    public void saveValues () {
        final BlueJManager manager = BlueJManager.getInstance();
        manager.setExtensionPropertyString(PROPERTY_PMD_PATH, pmdPath.getText());
        manager.setExtensionPropertyString(PROPERTY_PMD_OPTIONS, pmdOptions.getText());
    }

    /** @see bluej.extensions.PreferenceGenerator#loadValues() */
    public final void loadValues () {

        pmdPath.setText(getPMDPath());
        pmdOptions.setText(getPMDOptions());
    }

    /**
     * PMD Preference panel options with default options
     * @return String PMD default options
     */
    public final String getPMDOptions() {

        final BlueJManager manager = BlueJManager.getInstance();
        return manager.getExtensionPropertyString(PROPERTY_PMD_OPTIONS, PMD_OPTIONS_DEFAULT);
    }

    /**
     * PMD Preference panel allows user to set directory path to PMD
     * @return String  directory path to PMD executables
     */
    public final String getPMDPath() {
        final BlueJManager manager = BlueJManager.getInstance();
        return manager.getExtensionPropertyString(PROPERTY_PMD_PATH, "");
    }
}
