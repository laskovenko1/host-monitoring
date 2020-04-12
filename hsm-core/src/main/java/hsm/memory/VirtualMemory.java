package hsm.memory;

import hsm.units.InformationQuantity;

public class VirtualMemory extends AbstractMemory {

    public VirtualMemory(InformationQuantity usedQuantity, InformationQuantity availableQuantity) {
        super(usedQuantity, availableQuantity);
    }

    @Override
    public String toString() {
        return String.format("Virtual memory:\nUsed: %s,\t available: %s\n", getUsed().toString(), getAvailable().toString());
    }
}
