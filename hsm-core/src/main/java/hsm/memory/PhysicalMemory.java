package hsm.memory;

import hsm.units.InformationQuantity;

public class PhysicalMemory extends AbstractMemory {

    public PhysicalMemory(InformationQuantity usedQuantity, InformationQuantity availableQuantity) {
        super(usedQuantity, availableQuantity);
    }

    @Override
    public String toString() {
        return String.format("Physical memory:\nUsed: %s,\t available: %s\n", getUsed().toString(), getAvailable().toString());
    }
}
