import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinuxCPUMonitorTest extends LinuxAbstractTest {

    @Test
    public void getCpuUsageTest() {
        Map<String, Double> cpuUsage = hostStatus.getCpuMonitor().getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));

        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        cpuUsage.remove("-1");
        assertEquals(expectedProcessorsNum, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        assertEquals(expectedProcessorsNum, hostStatus.getCpuMonitor().getNumberOfCores());
    }
}
