package hsm.memory;

import hsm.units.InformationQuantity;

/**
 * This class represents a virtual memory (swap file) located on a system.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public class VirtualMemory {

    private final InformationQuantity used;
    private final InformationQuantity free;

    /**
     * Create a virtual memory instance.
     *
     * @param used used quantity of virtual memory
     * @param free free quantity of virtual memory
     */
    public VirtualMemory(InformationQuantity used, InformationQuantity free) {
        this.used = used;
        this.free = free;
    }

    /**
     * Get used quantity of virtual memory.
     *
     * @return used quantity of memory
     */
    public InformationQuantity getUsed() {
        return used;
    }

    /**
     * Get free quantity of virtual memory.
     *
     * @return free quantity of memory
     */
    public InformationQuantity getFree() {
        return free;
    }

    /**
     * Get string representation of virtual memory.
     *
     * @return string representation of virtual memory.
     */
    @Override
    public String toString() {
        return String.format("Virtual memory:\nUsed: %s,\t free: %s\n", used.toString(), free.toString());
    }
}
