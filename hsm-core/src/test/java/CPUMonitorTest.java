import hsm.HostStatusMonitor;
import hsm.monitors.CPUMonitor;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CPUMonitorTest {

    private final HostStatusMonitor hostStatusMonitor = new HostStatusMonitor();

    @Test
    public void getCpuUsageTest() {
        CPUMonitor cpuMonitor = hostStatusMonitor.getCpuMonitor();
        Map<String, Double> cpuUsage = cpuMonitor.getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));

        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        cpuUsage.remove("-1");
        assertEquals(expectedProcessorsNum, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        CPUMonitor cpuMonitor = hostStatusMonitor.getCpuMonitor();
        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        assertEquals(expectedProcessorsNum, cpuMonitor.getNumberOfCores());
    }
}
