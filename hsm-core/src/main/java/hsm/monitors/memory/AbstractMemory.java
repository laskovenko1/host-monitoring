package hsm.monitors.memory;

public abstract class AbstractMemory {

    private final MemoryQuantity usedQuantity;
    private final MemoryQuantity availableQuantity;

    public AbstractMemory(MemoryQuantity usedQuantity, MemoryQuantity availableQuantity) {
        this.usedQuantity = usedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public MemoryQuantity getUsed() {
        return usedQuantity;
    }

    public MemoryQuantity getAvailable() {
        return availableQuantity;
    }

    public abstract String toString();
}
