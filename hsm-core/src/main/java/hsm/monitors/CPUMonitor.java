package hsm.monitors;

import java.util.Map;

public interface CPUMonitor {

    Map<String, Double> getCpuUsage();

    int getNumberOfCores();
}
