import hsm.HostStatusMonitor;
import hsm.Platform;
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

    private static final HostStatusMonitor hostStatus = new HostStatusMonitor();

    @Before
    public void linuxOnly() {
        Assume.assumeTrue(HostStatusMonitor.getCurrentPlatform().equals(Platform.LINUX));
    }

    @Test
    public void getFilesystemsTest() {
        FilesystemMonitor monitor = hostStatus.getFilesystemMonitor();
        List<Filesystem> allFs = monitor.getFilesystems(null);
        assertFalse(allFs.isEmpty());
        assertEquals(allFs.size(), monitor.getFilesystems(new ArrayList<>()).size());
        List<Filesystem> wrongType = monitor.getFilesystems(Arrays.asList("TEST", "WRONG TYPE", "!@#$R!FEDF"));
        assertTrue(wrongType.isEmpty());
    }
}
