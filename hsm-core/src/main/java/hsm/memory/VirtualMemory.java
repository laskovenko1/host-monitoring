package hsm.memory;

import hsm.units.InformationQuantity;

/**
 * Represents a virtual memory (swap file) located on a system.
 */
public class VirtualMemory {

    private final InformationQuantity used;
    private final InformationQuantity free;

    public VirtualMemory(InformationQuantity used, InformationQuantity free) {
        this.used = used;
        this.free = free;
    }

    public InformationQuantity getUsed() {
        return used;
    }

    public InformationQuantity getFree() {
        return free;
    }

    @Override
    public String toString() {
        return String.format("Virtual memory:\nUsed: %s,\t free: %s\n", used.toString(), free.toString());
    }
}
