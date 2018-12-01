import com.SoftwareExtensionRenovators.toolbox.pmd.SystemUtils;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class SystemUtilsTest {

    @Test
    public void isWindowsTest() {

        Boolean result;
        String osname = System.getProperty("os.name");

        if(osname.toLowerCase(Locale.ROOT).contains("win")){
            result = true;
        }else{
            result = false;
        }
        Boolean system = SystemUtils.isWindows();
        assertEquals(system, result);

    }
}