import hsm.MonitorProvider;
import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import hsm.system.UnsupportedOperationSystemException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static hsm.system.OperatingSystem.LINUX;
import static hsm.system.OperatingSystem.WINDOWS;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MonitorProvider.class)
public class MonitorProviderTest {

    @Test
    public void testProviderInstantiating() {
        mockSystemInfoInstance(LINUX, WINDOWS);
        createProviderOrFail();
        createProviderAndFail();
    }

    private void mockSystemInfoInstance(OperatingSystem returnedOS, OperatingSystem... rest) {
        SystemInfo systemInfoMock = Mockito.mock(SystemInfo.class);
        when(systemInfoMock.getCurrentOS()).thenReturn(returnedOS, rest);
        try {
            PowerMockito.whenNew(SystemInfo.class).withNoArguments().thenReturn(systemInfoMock);
        } catch (Exception e) {
            fail("Error when mocking SystemInfo: " + e.getCause());
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
    public void checkProvidingMonitorInstances() {
        mockSystemInfoInstance(LINUX);
        MonitorProvider provider = new MonitorProvider();
        checkLinuxMonitorInstances(provider);
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
