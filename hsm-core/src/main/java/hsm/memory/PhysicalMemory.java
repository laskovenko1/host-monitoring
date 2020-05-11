package hsm.memory;

import hsm.units.InformationQuantity;

/**
 * Represents a physical memory located on a system and available to the operating system.
 */
public class PhysicalMemory {

    private final InformationQuantity used;
    private final InformationQuantity available;

    public PhysicalMemory(InformationQuantity used, InformationQuantity available) {
        this.used = used;
        this.available = available;
    }

    /**
     * Get used quantity of physical memory.
     */
    public InformationQuantity getUsed() {
        return used;
    }

    /**
     * Get available (free amount + used by cache/buffers) quantity of physical memory.
     */
    public InformationQuantity getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return String.format("Physical memory:\nUsed: %s,\t available: %s\n", used.toString(), available.toString());
    }
}
