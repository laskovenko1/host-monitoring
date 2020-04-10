package hsm.monitors.memory;

public class MemoryQuantity {

    private long value;
    private MemoryUnit unit;

    public MemoryQuantity(long value, MemoryUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public long getValue() {
        return value;
    }

    public MemoryUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return String.format("%d%s", value, unit.toString());
    }
}
