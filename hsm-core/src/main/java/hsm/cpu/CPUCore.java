package hsm.cpu;

import hsm.units.Percent;

/**
 * Represents central processing unit core.
 */
public class CPUCore {

    private final int number;
    private final Percent usage;

    public CPUCore(int number, Percent usage) {
        this.number = number;
        this.usage = usage;
    }

    public int getNumber() {
        return number;
    }

    public Percent getUsage() {
        return usage;
    }
}
