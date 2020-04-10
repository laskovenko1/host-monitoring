package hsm.monitors.memory;

public class PhysicalMemory extends AbstractMemory {
    public PhysicalMemory(MemoryQuantity usedQuantity, MemoryQuantity availableQuantity) {
        super(usedQuantity, availableQuantity);
    }

    @Override
    public String toString() {
        return String.format("Physical memory:\nUsed: %s,\t available: %s\n", getUsed().toString(), getAvailable().toString());
    }
}
