package com.SoftwareExtensionRenovators.toolbox.pmd;

import java.util.Locale;
/**
 * Checks which OS is being used by the user.
 * @author Ted Mittelstaedt, from an original by Tom Copeland
 * @author Egor Muscat, Jackie Nugent, Mark Huntington, Zac Champion
 * @version 0.6.1
 */

public class SystemUtils{
    /**
     * Checks to see if user's OS is Windows
     * @return boolean
     */
    public static boolean isWindows(){
        String osname = System.getProperty("os.name");
        return osname.toLowerCase(Locale.ROOT).contains("win");
    }
}
