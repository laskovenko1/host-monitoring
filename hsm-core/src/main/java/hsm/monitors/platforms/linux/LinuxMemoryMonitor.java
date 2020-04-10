package hsm.monitors.platforms.linux;

import hsm.monitors.MemoryMonitor;

import java.util.Map;

public final class LinuxMemoryMonitor implements MemoryMonitor {

    public static final String FREE_COMMAND = "free -h";

    @Override
    public Map<String, String> getUsedMemory() {
        return null;
    }

    @Override
    public Map<String, String> getFreeMemory() {
        return null;
    }

    private enum Memory {
        USED(2), FREE(3);

        private final int index;

        Memory(int index) {
            this.index = index;
        }
    }
}
