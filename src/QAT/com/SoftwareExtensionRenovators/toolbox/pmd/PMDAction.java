package com.SoftwareExtensionRenovators.toolbox.pmd;

import bluej.extensions.BPackage;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import com.SoftwareExtensionRenovators.toolbox.pmd.Preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PMDAction extends AbstractAction {
    private static final long serialVersionUID = 832198409175L;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private String javaDir;
    private com.SoftwareExtensionRenovators.toolbox.pmd.Preferences mPMDPref = new com.SoftwareExtensionRenovators.toolbox.pmd.Preferences();;
    private com.SoftwareExtensionRenovators.toolbox.pmd.Preferences mPMDPrefOptions = new com.SoftwareExtensionRenovators.toolbox.pmd.Preferences();;

    public PMDAction (Preferences preferences) {this.mPMDPref = preferences; }

    public PMDAction(String menuName,BPackage aPackage){
        this.determineJavaDir(aPackage);
        this.putValue("Name", menuName);
    }

    public void determineJavaDir(BPackage aPackage){
        try{
            this.javaDir = aPackage.getDir().getPath().toString();
        }catch (ProjectNotOpenException | PackageNotFoundException e){
            e.printStackTrace();
        }

    }

    public void actionPerformed(ActionEvent anEvent){

        if(this.javaDir != null && !this.javaDir.trim().isEmpty()){
            String pmdPath = PMDAction.this.mPMDPref.getPMDPath();
            String pmdOptions = PMDAction.this.mPMDPrefOptions.getPMDOptions();
            if(pmdPath != null && !pmdPath.trim().isEmpty()){
                try {
                    JOptionPane.showMessageDialog((Component) null, "Running PMD on all Classes (Click OK)");
                    String myCommand= pmdPath + "/bin/run.sh pmd " + pmdOptions + this.javaDir;
                    if(SystemUtils.isWindows()){
                        myCommand= pmdPath + "\\bin\\pmd.bat " + pmdOptions + this.javaDir;
                    }
                    String output = this.runPMD(myCommand);
                    JOptionPane.showMessageDialog((Component) null, "Project Checked");
                    StringBuilder msg = new StringBuilder("Any problems found are displayed below: ");
                    msg.append(LINE_SEPARATOR);
                    msg.append(output);
                    JOptionPane.showMessageDialog((Component) null, msg);

                } catch (IOException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog((Component) null, "Couldn't run PMD: " + e.getMessage(), "Exception", 0);

                } catch (InterruptedException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog((Component) null, "Couldn't run PMD" + e.getMessage(), "Exception", 0);

                }
                JOptionPane.showMessageDialog((Component) null, "PMD run completed");

            }else {
                JOptionPane.showMessageDialog((Component) null, "The path to PMD Installation is not configured. Please select the path under / Tools / Preferences / Extensions / PMD", "No Path to PMD Installation", 0);
            }


        } else{
            JOptionPane.showMessageDialog((Component) null, "No file selected");

        }

    }
    private String runPMD(String mycommand) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(mycommand.split(","));
        pb.redirectErrorStream(false);
        final Process p = pb.start();
        final StringBuilder output = new StringBuilder();
        Thread reader = new Thread(new Runnable() {
            public void run() {
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

                try {
                    String s;
                    try {
                        while((s = stdInput.readLine()) != null) {
                            output.append(s);
                            output.append(LINE_SEPARATOR);
                        }
                    } catch (IOException e) {
                        output.append(e.toString());
                        e.printStackTrace();
                    }
                } finally {
                    try {
                        stdInput.close();
                    } catch (IOException e) {
                        ;
                    }
                }
            }
        });
        reader.setDaemon(true);
        reader.start();
        p.waitFor();
        return output.toString();
    }

}