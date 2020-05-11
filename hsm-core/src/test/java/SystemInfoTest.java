import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static hsm.system.OperatingSystem.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemInfo.class)
public class SystemInfoTest {

    private static final String LINUX_OS_NAME = "Linux";
    private static final String WINDOWS_OS_NAME = "Windows";
    private static final String MAC_OS_NAME = "Mac";
    private static final String SOLARIS_OS_NAME = "Solaris";

    @Test
    public void testOperationSystemDetection() {
        mockSystem();
        checkLinuxDetection();
        checkWindowsDetection();
        checkMacDetection();
        checkUnknownSystemDetection();
        checkUnknownSystemDetection();
    }

    private void mockSystem() {
        PowerMockito.mockStatic(System.class);
        when(System.getProperty("os.name"))
                .thenReturn(LINUX_OS_NAME, WINDOWS_OS_NAME, MAC_OS_NAME, SOLARIS_OS_NAME)
                .thenThrow(SecurityException.class);
    }

    private void checkLinuxDetection() {
        checkOperationSystemDetection(LINUX);
    }

    private void checkWindowsDetection() {
        checkOperationSystemDetection(WINDOWS);
    }

    private void checkMacDetection() {
        checkOperationSystemDetection(MAC);
    }

    private void checkUnknownSystemDetection() {
        checkOperationSystemDetection(UNKNOWN);
    }

    private void checkOperationSystemDetection(OperatingSystem expectedOS) {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem detectedOS = systemInfo.getCurrentOS();
        assertEquals(expectedOS, detectedOS);
    }
}
