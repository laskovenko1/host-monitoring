package hsm.cpu;

import hsm.units.Percent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentralProcessingUnit {

    private final List<CPUCore> cores;

    public CentralProcessingUnit(List<CPUCore> cores) {
        this.cores = new ArrayList<>();
        this.cores.addAll(cores);
    }

    public List<CPUCore> getCores() {
        return Collections.unmodifiableList(cores);
    }

    /**
     * Get average CPU usage.
     *
     * @return an average usage percent among all CPU cores.
     */
    public Percent getAverageUsage() {
        if (cores.isEmpty())
            return Percent.ZERO;
        return countAverageUsage();
    }

    private Percent countAverageUsage() {
        Double allUsage = cores.stream()
                .map(CPUCore::getUsage)
                .map(Percent::toFraction)
                .reduce(0d, Double::sum);
        double averageValue = allUsage / cores.size();
        return new Percent(averageValue);
    }
}
