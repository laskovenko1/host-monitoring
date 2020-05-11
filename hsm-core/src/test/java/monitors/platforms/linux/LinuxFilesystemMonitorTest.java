package monitors.platforms.linux;

import hsm.MonitorProvider;
import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import hsm.filesystem.Filesystem;
import hsm.monitors.FilesystemMonitor;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LinuxFilesystemMonitorTest {

    @Before
    public void linuxOnly() {
        SystemInfo systemInfo = new SystemInfo();
        Assume.assumeTrue(systemInfo.getCurrentOS().equals(OperatingSystem.LINUX));
    }

    @Test
    public void getFilesystemsTest() {
        MonitorProvider monitorProvider = new MonitorProvider();
        FilesystemMonitor filesystemMonitor = monitorProvider.getFilesystemMonitor();

        assertEquals(filesystemMonitor.getFilesystems(null), filesystemMonitor.getFilesystems(new ArrayList<>()));

        List<Filesystem> allFs = filesystemMonitor.getFilesystems(null);
        assertFalse(allFs.isEmpty());

        List<Filesystem> wrongType = filesystemMonitor.getFilesystems(Arrays.asList("TEST", "WRONG TYPE", "!@#$R!FEDF"));
        assertTrue(wrongType.isEmpty());
    }
}
