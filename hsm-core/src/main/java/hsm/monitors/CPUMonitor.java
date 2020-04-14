package hsm.monitors;

import java.util.Map;

/**
 * This interface represents API for Central Processing Unit (CPU) monitor of a hosts system.
 */
public interface CPUMonitor {

    /**
     * Get the CPU usage statistics for each logical CPU processor and average value among all processors.
     *
     * @return a map where keys are string identifies (may be ordinal numbers) of logical CPU processor
     * and values are doubles in range [0.0 - 100.0] that represents average percentage of usage the processor.
     * @apiNote It's required to specify special key-value pair for average usage statistics among all CPU processors.
     * @implNote The way in which the average CPU usage percentage is calculated is platform specific.
     */
    Map<String, Double> getCpuUsage();

    /**
     * Get the number of logical CPU cores available in host's system.
     *
     * @return the number of logical CPU cores available.
     * @apiNote This value can be more than the physical CPU processors amount.
     */
    int getNumberOfCores();
}
