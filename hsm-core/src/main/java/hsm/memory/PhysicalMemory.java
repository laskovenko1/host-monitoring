package hsm.memory;

import hsm.units.InformationQuantity;

public class PhysicalMemory {

    private final InformationQuantity used;
    private final InformationQuantity available;

    public PhysicalMemory(InformationQuantity used, InformationQuantity available) {
        this.used = used;
        this.available = available;
    }

    public InformationQuantity getUsed() {
        return used;
    }

    public InformationQuantity getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return String.format("Physical memory:\nUsed: %s,\t available: %s\n", used.toString(), available.toString());
    }
}
