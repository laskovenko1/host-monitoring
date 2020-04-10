import org.host.monitoring.CPUMonitor;
import org.host.monitoring.CPUMonitorImpl;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CPUMonitorTest {

    @Test
    public void getCpuUsageTest() {
        CPUMonitor monitor = new CPUMonitorImpl();
        Map<String, Double> cpuUsage = monitor.getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));

        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        cpuUsage.remove("-1");
        assertEquals(expectedProcessorsNum, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        CPUMonitor monitor = new CPUMonitorImpl();
        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        assertEquals(expectedProcessorsNum, monitor.getNumberOfCores());
    }
}
