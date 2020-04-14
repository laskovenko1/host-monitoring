package hsm.monitors;

import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;

/**
 * This interface represents API for memory monitor.
 */
public interface MemoryMonitor {

    /**
     * Get physical memory available to the host's operating system.
     *
     * @return physical memory
     */
    PhysicalMemory getPhysicalMemory();

    /**
     * Get virtual memory.
     *
     * @return virtual memory
     */
    VirtualMemory getVirtualMemory();
}
