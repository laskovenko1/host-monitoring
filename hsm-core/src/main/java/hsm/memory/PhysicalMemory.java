package hsm.memory;

import hsm.units.InformationQuantity;

/**
 * This class represents a physical memory located on a system and available to the operating system.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public class PhysicalMemory {

    private final InformationQuantity used;
    private final InformationQuantity available;

    /**
     * Create a physical memory instance.
     *
     * @param used      used quantity of physical memory
     * @param available available quantity of physical memory
     */
    public PhysicalMemory(InformationQuantity used, InformationQuantity available) {
        this.used = used;
        this.available = available;
    }

    /**
     * Get used quantity of physical memory.
     *
     * @return used quantity of memory
     */
    public InformationQuantity getUsed() {
        return used;
    }

    /**
     * Get available (free amount + used by cache/buffers) quantity of physical memory.
     *
     * @return available quantity of memory
     */
    public InformationQuantity getAvailable() {
        return available;
    }

    /**
     * Get string representation of physical memory.
     *
     * @return string representation of physical memory.
     */
    @Override
    public String toString() {
        return String.format("Physical memory:\nUsed: %s,\t available: %s\n", used.toString(), available.toString());
    }
}
