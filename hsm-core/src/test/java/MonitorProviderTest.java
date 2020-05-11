import hsm.MonitorProvider;
import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import hsm.system.UnsupportedOperationSystemException;
import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import org.junit.Assume;
import org.junit.Test;

import static hsm.system.OperatingSystem.LINUX;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MonitorProviderTest {

    private final OperatingSystem currentOS;

    public MonitorProviderTest() {
        SystemInfo systemInfo = new SystemInfo();
        currentOS = systemInfo.getCurrentOS();
    }

    @Test
    public void providerInstantiatingTest() {
        if (currentOS.isSupported()) {
            createProviderOrFail();
        } else {
            createProviderAndFail();
        }
    }

    private void createProviderOrFail() {
        try {
            new MonitorProvider();
        } catch (UnsupportedOperationSystemException e) {
            fail();
        }
    }

    private void createProviderAndFail() {
        try {
            new MonitorProvider();
            fail();
        } catch (UnsupportedOperationSystemException ignored) {
        }
    }

    @Test
    public void checkProvidingInstances() {
        Assume.assumeTrue(currentOS.isSupported());
        MonitorProvider provider = new MonitorProvider();
        if (currentOS == LINUX) {
            checkLinuxMonitorInstances(provider);
        }
    }

    private void checkLinuxMonitorInstances(MonitorProvider monitorProvider) {
        CPUMonitor cpuMonitor = monitorProvider.getCpuMonitor();
        FilesystemMonitor filesystemMonitor = monitorProvider.getFilesystemMonitor();
        MemoryMonitor memoryMonitor = monitorProvider.getMemoryMonitor();
        assertTrue(cpuMonitor instanceof LinuxCPUMonitor);
        assertTrue(filesystemMonitor instanceof LinuxFilesystemMonitor);
        assertTrue(memoryMonitor instanceof LinuxMemoryMonitor);
    }
}
