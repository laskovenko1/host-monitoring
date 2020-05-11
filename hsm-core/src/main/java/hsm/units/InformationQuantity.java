package hsm.units;

/**
 * Represents quantity of information.
 * The unit for this quantity is "B" (Byte) with (or without) binary prefix.
 */
public class InformationQuantity {

    private long bytes;
    private BinaryPrefix prefix;

    /**
     * @param bytes  value of the quantity in bytes.
     * @param prefix binary prefix or {@code null} if the unit should be without any binary prefix.
     */
    public InformationQuantity(long bytes, BinaryPrefix prefix) {
        this.bytes = bytes;
        this.prefix = prefix;
    }

    public long getBytes() {
        return bytes;
    }

    public BinaryPrefix getPrefix() {
        return prefix;
    }

    public void setPrefix(BinaryPrefix prefix) {
        this.prefix = prefix;
    }

    /**
     * @implSpec Two quantities are equal when they both have the same byte values
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof InformationQuantity))
            return false;

        InformationQuantity that = (InformationQuantity) o;
        return bytes == that.bytes;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bytes);
    }

    /**
     * Get string representation of the quantity of information.
     * <p>
     * For example, "224KiB".
     */
    @Override
    public String toString() {
        if (prefix == null)
            return String.format("%dB", bytes);

        return String.format("%d%sB", bytes >> prefix.getPower(), prefix.toString());
    }
}
