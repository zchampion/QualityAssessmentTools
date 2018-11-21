package com.SoftwareExtensionRenovators.toolbox.pmd;

import java.util.Locale;

public class SystemUtils{
    public static boolean isWindows(){
        String osname = System.getProperty("os.name");
        return osname.toLowerCase(Locale.ROOT).contains("win");
    }
}