import hsm.MonitorProvider;
import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import hsm.monitors.CPUMonitor;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinuxCPUMonitorTest {

    private final int availableProcessors = Runtime.getRuntime().availableProcessors();

    @Before
    public void linuxOnly() {
        SystemInfo systemInfo = new SystemInfo();
        Assume.assumeTrue(systemInfo.getCurrentOS().equals(OperatingSystem.LINUX));
    }

    @Test
    public void getCpuUsageTest() {
        MonitorProvider monitorProvider = new MonitorProvider();
        CPUMonitor cpuMonitor = monitorProvider.getCpuMonitor();

        Map<String, Double> cpuUsage = cpuMonitor.getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));
        cpuUsage.remove("-1");
        assertEquals(availableProcessors, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        MonitorProvider monitorProvider = new MonitorProvider();
        CPUMonitor cpuMonitor = monitorProvider.getCpuMonitor();

        assertEquals(availableProcessors, cpuMonitor.getNumberOfCores());
    }
}
