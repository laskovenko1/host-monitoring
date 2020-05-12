package hsm.monitors;

import hsm.cpu.CentralProcessingUnit;

/**
 * API for Central Processing Unit (CPU) monitoring.
 */
public interface CPUMonitor {

    /**
     * Get {@link CentralProcessingUnit} object.
     *
     * @implNote The way in which the average CPU usage percentage is calculated is platform specific.
     */
    CentralProcessingUnit getCPU();
}
