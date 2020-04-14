package hsm.units;

/**
 * Binary prefixes represent multiplication by powers of two.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Binary_prefix">Wikipedia: Binary Prefix</a>
 */
public enum BinaryPrefix {
    /**
     * Kibi - prefix for 2<sup>10</sup>.
     */
    Ki(10),
    /**
     * Mebi - prefix for 2<sup>20</sup>.
     */
    Mi(20),
    /**
     * Gibi - prefix for 2<sup>30</sup>.
     */
    Gi(30),
    /**
     * Tebi - prefix for 2<sup>40</sup>.
     */
    Ti(40),
    /**
     * Pebi - prefix for 2<sup>50</sup>.
     */
    Pi(50);

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