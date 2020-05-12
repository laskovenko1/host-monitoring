package hsm.units;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Represents percentage quantity.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Percentage">Wikipedia: Percentage</a>
 */
public class Percent {

    public static final Percent ZERO = new Percent(0);

    private static final double EPSILON = 0.01d;

    /**
     * Fractions of 100.
     */
    private final BigDecimal fractions;

    public Percent(double fractions) {
        this.fractions = new BigDecimal(fractions).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Get a percent as a number of fractions.
     */
    public double toFraction() {
        return fractions.doubleValue();
    }

    /**
     * @implSpec Two percents are equal when their fraction values are equal with accuracy of {@value EPSILON}.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Percent))
            return false;

        Percent p = (Percent) o;
        return Math.abs(this.fractions.doubleValue() - p.fractions.doubleValue()) <= EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(fractions.doubleValue());
    }

    @Override
    public String toString() {
        return String.format("%.2f%%", fractions);
    }
}
