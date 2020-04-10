package hsm.monitors;

import java.util.Map;

public interface MemoryMonitor {

    Map<String, String> getUsedMemory();

    Map<String, String> getFreeMemory();
}
