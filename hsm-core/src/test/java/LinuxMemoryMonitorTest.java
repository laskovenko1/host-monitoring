import hsm.HostStatusMonitor;
import hsm.Platform;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.memory.MemoryUnit;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class LinuxMemoryMonitorTest {

    private static final MemoryMonitor memoryMonitor = new HostStatusMonitor().getMemoryMonitor();

    @Before
    public void linuxOnly() {
        Assume.assumeTrue(HostStatusMonitor.getCurrentPlatform().equals(Platform.LINUX));
    }

    @Test
    public void getPhysicalMemoryTest() {
        System.out.println(memoryMonitor.getPhysicalMemory(MemoryUnit.MB));
    }

    @Test
    public void getVirtualMemoryTest() {
        System.out.println(memoryMonitor.getVirtualMemory(MemoryUnit.MB));
    }
}
