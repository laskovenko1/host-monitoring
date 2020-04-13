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

public class LinuxFilesystemMonitorTest extends LinuxAbstractTest {

    @Test
    public void getFilesystemsTest() {
        FilesystemMonitor monitor = hostStatus.getFilesystemMonitor();

        assertEquals(monitor.getFilesystems(null), monitor.getFilesystems(new ArrayList<>()));

        List<Filesystem> allFs = monitor.getFilesystems(null);
        assertFalse(allFs.isEmpty());

        List<Filesystem> wrongType = monitor.getFilesystems(Arrays.asList("TEST", "WRONG TYPE", "!@#$R!FEDF"));
        assertTrue(wrongType.isEmpty());
    }
}
