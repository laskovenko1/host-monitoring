package hsm.monitors;

import hsm.monitors.memory.MemoryUnit;
import hsm.monitors.memory.PhysicalMemory;
import hsm.monitors.memory.VirtualMemory;

public interface MemoryMonitor {

    PhysicalMemory getPhysicalMemory(MemoryUnit unit);

    VirtualMemory getVirtualMemory(MemoryUnit unit);
}
