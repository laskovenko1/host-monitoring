package monitors.platforms.linux;

import hsm.MonitorProvider;
import hsm.monitors.CPUMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.utils.CommonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtils.class)
public class LinuxCPUMonitorTest {

    private static final String MPSTAT_COMMAND = "mpstat -P ALL 1 1";
    private static final String MPSTAT_OUTPUT_FILE = "mpstat_output.out";
    private static final int AVAILABLE_PROCESSORS = 12;

    private MonitorProvider monitorProvider;

    @Before
    public void setUpMocks() {
        mockMonitorProvider();
        mockCommonUtils();
    }

    private void mockMonitorProvider() {
        monitorProvider = Mockito.mock(MonitorProvider.class);
        when(monitorProvider.getCpuMonitor()).thenReturn(new LinuxCPUMonitor());
    }

    private void mockCommonUtils() {
        PowerMockito.mockStatic(CommonUtils.class);
        when(CommonUtils.executeCommand(MPSTAT_COMMAND)).thenReturn(new ProcessStub(MPSTAT_OUTPUT_FILE));
    }

    @Test
    public void getCpuUsageTest() {
        CPUMonitor cpuMonitor = monitorProvider.getCpuMonitor();

        Map<String, Double> cpuUsage = cpuMonitor.getCpuUsage();
        assertTrue(cpuUsage.containsKey("-1"));
        cpuUsage.remove("-1");
        assertEquals(AVAILABLE_PROCESSORS, cpuUsage.size());
    }

    @Test
    public void getNumberOfCores() {
        CPUMonitor cpuMonitor = monitorProvider.getCpuMonitor();

        assertEquals(AVAILABLE_PROCESSORS, cpuMonitor.getNumberOfCores());
    }
}
