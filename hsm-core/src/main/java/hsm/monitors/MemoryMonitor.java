package hsm.monitors;

import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;

/**
 * This interface represents API for memory monitoring.
 */
public interface MemoryMonitor {

    /**
     * Get physical memory available to the host's operating system.
     */
    PhysicalMemory getPhysicalMemory();

    /**
     * Get virtual memory.
     */
    VirtualMemory getVirtualMemory();
}
