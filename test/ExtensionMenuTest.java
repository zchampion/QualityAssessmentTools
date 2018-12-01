import bluej.extensions.BClass;
import bluej.extensions.BPackage;
import bluej.extensions.BlueJ;
import bluej.extensions.MenuGenerator;
import com.SoftwareExtensionRenovators.bluejmanager.ExtensionMenu;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class ExtensionMenuTest {


    @Test
    public void getToolsMenuItemTest() {

    }

    @Test
    public void getMenuItemTest() {

        ExtensionMenu object = new ExtensionMenu();
        JMenuItem getMenu = object.getMenuItem();
        assertNotNull(getMenu);
    }
}
