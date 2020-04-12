import hsm.HostStatusMonitor;
import hsm.Platform;
import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;
import hsm.monitors.MemoryMonitor;
import hsm.units.BinaryPrefix;
import hsm.units.InformationQuantity;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinuxMemoryMonitorTest {

    private static final MemoryMonitor memoryMonitor = new HostStatusMonitor().getMemoryMonitor();

    @Before
    public void linuxOnly() {
        Assume.assumeTrue(HostStatusMonitor.getCurrentPlatform().equals(Platform.LINUX));
    }

    @Test
    public void getPhysicalMemoryTest() {
        PhysicalMemory memory = memoryMonitor.getPhysicalMemory();
        assertNotNull(memory);

        InformationQuantity usedSize = memory.getUsed();
        assertTrue(usedSize.getBytes() > 0);
        assertNull(usedSize.getPrefix());
        assertTrue(usedSize.toString().matches("\\d+[B]"));
        assertEquals(usedSize.getBytes(), Long.parseLong(usedSize.toString().replaceAll("\\D", "")));
        assertSizeConversion(usedSize);

        InformationQuantity availableSize = memory.getAvailable();
        assertTrue(availableSize.getBytes() > 0);
        assertNull(availableSize.getPrefix());
        assertTrue(availableSize.toString().matches("\\d+[B]"));
        assertEquals(availableSize.getBytes(), Long.parseLong(availableSize.toString().replaceAll("\\D", "")));
        assertSizeConversion(availableSize);
    }

    @Test
    public void getVirtualMemoryTest() {
        VirtualMemory memory = memoryMonitor.getVirtualMemory();
    }

    private void assertSizeConversion(InformationQuantity size) {
        size.setPrefix(BinaryPrefix.Ki);
        assertEquals(Math.floorDiv(size.getBytes(), Double.valueOf(Math.pow(2, 10)).longValue()),
                Long.parseLong(size.toString().replaceAll("\\D", "")));
        size.setPrefix(BinaryPrefix.Mi);
        assertEquals(Math.floorDiv(size.getBytes(), Double.valueOf(Math.pow(2, 20)).longValue()),
                Long.parseLong(size.toString().replaceAll("\\D", "")));
        size.setPrefix(BinaryPrefix.Gi);
        assertEquals(Math.floorDiv(size.getBytes(), Double.valueOf(Math.pow(2, 30)).longValue()),
                Long.parseLong(size.toString().replaceAll("\\D", "")));
        size.setPrefix(BinaryPrefix.Ti);
        assertEquals(Math.floorDiv(size.getBytes(), Double.valueOf(Math.pow(2, 40)).longValue()),
                Long.parseLong(size.toString().replaceAll("\\D", "")));
        size.setPrefix(BinaryPrefix.Pi);
        assertEquals(Math.floorDiv(size.getBytes(), Double.valueOf(Math.pow(2, 50)).longValue()),
                Long.parseLong(size.toString().replaceAll("\\D", "")));
    }
}
