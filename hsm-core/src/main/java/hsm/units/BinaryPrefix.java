package hsm.units;

/**
 * Binary prefixes represent multiplication by powers of two.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Binary_prefix">Wikipedia: Binary Prefix</a>
 */
public enum BinaryPrefix {
    Ki(10), Mi(20), Gi(30), Ti(40), Pi(50);

    private final int power;

    BinaryPrefix(int power) {
        this.power = power;
    }

    /**
     * Get a power of the form where number two as the base and integer as the power.
     *
     * @return a power
     */
    public long getPower() {
        return power;
    }
}