package hsm.monitors.platform.linux;

import hsm.monitors.MemoryMonitor;

import java.util.Map;

public class LinuxMemoryMonitor implements MemoryMonitor {

    public static final String FREE_COMMAND = "free -h";

    enum Memory {
        USED(2), FREE(3);

        private final int index;

        Memory(int index) {
            this.index = index;
        }

        public int index() {
            return index;
        }

    }

    @Override
    public Map<String, String> getUsedMemory() {
        return null;
    }

    @Override
    public Map<String, String> getFreeMemory() {
        return null;
    }
}
