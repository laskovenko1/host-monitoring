import hsm.MonitorSupplier;
import hsm.OperatingSystem;
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
        Assume.assumeTrue(OperatingSystem.getCurrentOS().equals(OperatingSystem.LINUX));
    }

    @Test
    public void getCpuUsageTest() {
        MonitorSupplier monitorSupplier = new MonitorSupplier();
        CPUMonitor cpuMonitor = monitorSupplier.getCpuMonitor();

        Map<String, Double> cpuUsage = cpuMonitor.getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));
        cpuUsage.remove("-1");
        assertEquals(availableProcessors, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        MonitorSupplier monitorSupplier = new MonitorSupplier();
        CPUMonitor cpuMonitor = monitorSupplier.getCpuMonitor();

        assertEquals(availableProcessors, cpuMonitor.getNumberOfCores());
    }
}
