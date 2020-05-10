import hsm.MonitorSupplier;
import hsm.OperatingSystem;
import hsm.UnsupportedOperationSystemException;
import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import org.junit.Test;

import static hsm.OperatingSystem.*;
import static org.junit.Assert.*;

public class MonitorCreationTest {

    private final String actualOSName = System.getProperty("os.name");
    private final OperatingSystem detectedOS = OperatingSystem.getCurrentOS();

    @Test
    public void operationSystemDetectionTest() {
        if (isLinux())
            assertEquals(detectedOS, LINUX);
        else if (isWindows())
            assertEquals(detectedOS, WINDOWS);
        else if (isMac())
            assertEquals(detectedOS, MAC);
        else if (isUnspecified())
            assertEquals(detectedOS, UNSPECIFIED);
        else
            fail("Wrong OS detection.");
    }

    private boolean isLinux() {
        return actualOSName.contains("Linux");
    }

    private boolean isWindows() {
        return actualOSName.contains("Windows");
    }

    private boolean isMac() {
        return actualOSName.contains("Mac");
    }

    private boolean isUnspecified() {
        return actualOSName == null;
    }

    @Test
    public void monitorCreationTest() {
        if (isLinux()) {
            try {
                MonitorSupplier monitorSupplier = new MonitorSupplier();
                testLinuxMonitorInstances(monitorSupplier);
            } catch (UnsupportedOperationSystemException e) {
                fail();
            }
        } else {
            try {
                new MonitorSupplier();
                fail();
            } catch (UnsupportedOperationSystemException ignored) {
            }
        }
    }

    private void testLinuxMonitorInstances(MonitorSupplier monitorSupplier) {
        CPUMonitor cpuMonitor = monitorSupplier.getCpuMonitor();
        FilesystemMonitor filesystemMonitor = monitorSupplier.getFilesystemMonitor();
        MemoryMonitor memoryMonitor = monitorSupplier.getMemoryMonitor();
        assertTrue(cpuMonitor instanceof LinuxCPUMonitor);
        assertTrue(filesystemMonitor instanceof LinuxFilesystemMonitor);
        assertTrue(memoryMonitor instanceof LinuxMemoryMonitor);
    }
}
