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
        assertTrue(cpuUsage.containsKey("ALL"));
        assertProcessorsNum(cpuUsage.size() - 1);
    }

    @Test
    public void getCpuNumberTest() {
        CPUMonitor monitor = new CPUMonitorImpl();
        assertProcessorsNum(monitor.getCpuNumber());
    }

    private void assertProcessorsNum(int actual) {
        int expectedProcessorsNum = Runtime.getRuntime().availableProcessors();
        assertEquals(expectedProcessorsNum, actual);
    }
}
