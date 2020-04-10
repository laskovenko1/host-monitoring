package hsm.monitors.memory;

public class VirtualMemory extends AbstractMemory {
    public VirtualMemory(MemoryQuantity usedQuantity, MemoryQuantity availableQuantity) {
        super(usedQuantity, availableQuantity);
    }

    @Override
    public String toString() {
        return String.format("Virtual memory:\nUsed: %s,\t available: %s\n", getUsed().toString(), getAvailable().toString());
    }
}
