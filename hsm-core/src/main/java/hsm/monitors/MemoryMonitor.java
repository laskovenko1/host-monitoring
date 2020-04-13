package hsm.monitors;

import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;

public interface MemoryMonitor {

    PhysicalMemory getPhysicalMemory();

    VirtualMemory getVirtualMemory();
}
