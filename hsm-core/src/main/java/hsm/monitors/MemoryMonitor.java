package hsm.monitors;

import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;

/**
 * API for memory monitoring.
 */
public interface MemoryMonitor {

    /**
     * Get physical memory available to the host's operating system.
     */
    PhysicalMemory getPhysicalMemory();

    /**
     * Get virtual memory available to the host's operating system.
     */
    VirtualMemory getVirtualMemory();
}
