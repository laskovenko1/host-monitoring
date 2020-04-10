package org.host.monitoring;

import java.util.Map;

public interface CPUMonitor {

    Map<String, Double> getCpuUsage();

    int getNumberOfCores();
}
