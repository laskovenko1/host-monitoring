package hsm.units;

/**
 * This class represents quantity of information.
 * The unit for this quantity is "B" (Byte) with (or without) binary prefix.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public class InformationQuantity {

    private long bytes;
    private BinaryPrefix prefix;

    /**
     * Create quantity of information instance.
     *
     * @param bytes  value of the quantity in bytes
     * @param prefix binary prefix. Can be null that means the unit will be without any binary prefix
     */
    public InformationQuantity(long bytes, BinaryPrefix prefix) {
        this.bytes = bytes;
        this.prefix = prefix;
    }

    /**
     * Get the value of the quantity in bytes.
     *
     * @return a value in bytes
     */
    public long getBytes() {
        return bytes;
    }

    /**
     * Get the binary prefix of the unit of quantity.
     *
     * @return binary prefix
     */
    public BinaryPrefix getPrefix() {
        return prefix;
    }

    /**
     * Set the binary prefix of the unit of quantity.
     *
     * @param prefix binary prefix
     */
    public void setPrefix(BinaryPrefix prefix) {
        this.prefix = prefix;
    }

    /**
     * Check if other object is equal to the quantity.
     *
     * @param o object to be compared with
     * @return true if the quantity is equal to object or false
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

    /**
     * Overridden {@code Object.hashCode()} method to maintain the general contract.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Long.hashCode(bytes);
    }

    /**
     * Get string representation of the quantity of information.
     *
     * @return string representation of the quantity of information. For example, "224KiB"
     */
    @Override
    public String toString() {
        if (prefix == null)
            return String.format("%dB", bytes);

        return String.format("%d%sB", bytes >> prefix.getPower(), prefix.toString());
    }
}
