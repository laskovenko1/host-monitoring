package units;

import hsm.units.InformationQuantity;
import org.junit.Test;

import static hsm.units.BinaryPrefix.*;
import static org.junit.Assert.assertEquals;

public class InformationQuantityTest {

    private final InformationQuantity quantity = new InformationQuantity(1662216601600L, null);

    @Test
    public void testUnitsConversion() {
        quantity.setPrefix(Ki);
        assertEquals(convertBytes(Ki.getPower()), parseQuantity(quantity));

        quantity.setPrefix(Mi);
        assertEquals(convertBytes(Mi.getPower()), parseQuantity(quantity));

        quantity.setPrefix(Gi);
        assertEquals(convertBytes(Gi.getPower()), parseQuantity(quantity));

        quantity.setPrefix(Ti);
        assertEquals(convertBytes(Ti.getPower()), parseQuantity(quantity));

        quantity.setPrefix(Pi);
        assertEquals(convertBytes(Pi.getPower()), parseQuantity(quantity));
    }

    private long convertBytes(long prefixPower) {
        return Math.floorDiv(quantity.getBytes(), Double.valueOf(Math.pow(2, prefixPower)).longValue());
    }

    private long parseQuantity(InformationQuantity quantity) {
        return Long.parseLong(quantity.toString().replaceAll("\\D", ""));
    }
}
