package com.SoftwareExtensionRenovators.toolbox.pmd;

import bluej.extensions.BPackage;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PMDAction extends AbstractAction {

    private static final long serialVersionUID = 832198409175L;

    private String javaDir;

    private static final String LINE_SEPARATOR = System.getProperty(("line.separator"));

    public void determineJavaFileName(BPackage aPackage){
        try{
            this.javaDir = aPackage.getDir().getPath().toString();
        }catch (ProjectNotOpenException | PackageNotFoundException e){
            e.printStackTrace();
        }

    }
    public PMDAction(BPackage aPackage){
        this.determineJavaFileName(aPackage);

    }

    public void actionPerformed(ActionEvent anEvent){

        if(this.javaDir != null && !this.javaDir.trim().isEmpty()){
            //String pmdPath = "G:/programs/bluej/lib/extensions/pmd-bin-6.9.0";
            String pmdPath = "Users/jackienugent/pmd-bin-6.9.0";
            if(pmdPath != null && !pmdPath.trim().isEmpty()){
                try {
                    JOptionPane.showMessageDialog((Component) null, "Running PMD on all Classes (Click OK)");
                    String myCommand = pmdPath + "/bin/run.sh" + ",pmd,-format,text,-R,java-quickstart,-version,1.8,-language,java,-no-cache,-d," + this.javaDir;
                    if(SystemUtils.isWindows()){
                        myCommand = pmdPath + "\\bin\\pmd.bat" + ",-format,text,-R,java-quickstart,-version,1.8,-language,java,-no-cache,-d," + this.javaDir;

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