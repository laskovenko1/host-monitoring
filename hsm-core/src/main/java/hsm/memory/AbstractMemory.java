package hsm.memory;

import hsm.units.InformationQuantity;

public abstract class AbstractMemory {

    private final InformationQuantity used;
    private final InformationQuantity available;

    public AbstractMemory(InformationQuantity used, InformationQuantity available) {
        this.used = used;
        this.available = available;
    }

    public InformationQuantity getUsed() {
        return used;
    }

    public InformationQuantity getAvailable() {
        return available;
    }

    public abstract String toString();
}
