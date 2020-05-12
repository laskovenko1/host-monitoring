package monitors.platforms.linux;

import hsm.MonitorProvider;
import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import hsm.monitors.utils.CommonUtils;
import hsm.units.InformationQuantity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtils.class)
public class LinuxMemoryMonitorTest {

    private static final String FREE_COMMAND = "free -b";
    private static final String FREE_OUTPUT_FILE = "free_output.out";
    private static final InformationQuantity USED_PHYSICAL_MEMORY = new InformationQuantity(5408968704L, null);
    private static final InformationQuantity AVAILABLE_PHYSICAL_MEMORY = new InformationQuantity(10720518144L, null);
    private static final InformationQuantity USED_VIRTUAL_MEMORY = new InformationQuantity(10L, null);
    private static final InformationQuantity FREE_VIRTUAL_MEMORY = new InformationQuantity(2147479542L, null);

    private MonitorProvider monitorProvider;

    @Before
    public void setUpMocks() {
        mockMonitorProvider();
        mockCommonUtils();
    }

    private void mockMonitorProvider() {
        monitorProvider = Mockito.mock(MonitorProvider.class);
        when(monitorProvider.getMemoryMonitor()).thenReturn(new LinuxMemoryMonitor());
    }

    private void mockCommonUtils() {
        PowerMockito.mockStatic(CommonUtils.class);
        when(CommonUtils.executeCommand(FREE_COMMAND)).thenReturn(new ProcessStub(FREE_OUTPUT_FILE));
    }

    @Test
    public void getPhysicalMemoryTest() {
        MemoryMonitor memoryMonitor = monitorProvider.getMemoryMonitor();

        PhysicalMemory memory = memoryMonitor.getPhysicalMemory();
        assertNotNull(memory);

        InformationQuantity memoryUsed = memory.getUsed();
        assertNull(memoryUsed.getPrefix());
        assertTrue(memoryUsed.toString().matches("\\d+[B]"));
        assertEquals(USED_PHYSICAL_MEMORY, memoryUsed);

        InformationQuantity memoryAvailable = memory.getAvailable();
        assertNull(memoryAvailable.getPrefix());
        assertTrue(memoryAvailable.toString().matches("\\d+[B]"));
        assertEquals(AVAILABLE_PHYSICAL_MEMORY, memoryAvailable);
    }

    @Test
    public void getVirtualMemoryTest() {
        MemoryMonitor memoryMonitor = monitorProvider.getMemoryMonitor();

        VirtualMemory memory = memoryMonitor.getVirtualMemory();
        assertNotNull(memory);

        InformationQuantity memoryUsed = memory.getUsed();
        assertNull(memoryUsed.getPrefix());
        assertTrue(memoryUsed.toString().matches("\\d+[B]"));
        assertEquals(USED_VIRTUAL_MEMORY, memoryUsed);

        InformationQuantity memoryFree = memory.getFree();
        assertNull(memoryFree.getPrefix());
        assertTrue(memoryFree.toString().matches("\\d+[B]"));
        assertEquals(FREE_VIRTUAL_MEMORY, memoryFree);
    }
}
