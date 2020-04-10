package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import org.apache.commons.lang3.SystemUtils;

import java.util.function.Supplier;

public final class HostStatusMonitor {

    private static final Platform currentPlatform;

    static {
        if (SystemUtils.IS_OS_LINUX)
            currentPlatform = Platform.LINUX;
        else if (SystemUtils.IS_OS_WINDOWS)
            currentPlatform = Platform.WINDOWS;
        else
            currentPlatform = Platform.UNKNOWN;
    }

    public static Platform getCurrentPlatform() {
        return currentPlatform;
    }

    private final Supplier<CPUMonitor> cpuMonitor = this::createCpuMonitor;
    private final Supplier<MemoryMonitor> memoryMonitor = this::createMemoryMonitor;

    public CPUMonitor getCpuMonitor() {
        return cpuMonitor.get();
    }

    public MemoryMonitor getMemoryMonitor() {
        return memoryMonitor.get();
    }

    private CPUMonitor createCpuMonitor() {
        switch (currentPlatform) {
            case LINUX:
                return new LinuxCPUMonitor();
            default:
                throw new UnsupportedOperationException("OS is not supported: " + SystemUtils.OS_NAME);
        }
    }

    private MemoryMonitor createMemoryMonitor() {
        switch (currentPlatform) {
            case LINUX:
                return new LinuxMemoryMonitor();
            default:
                throw new UnsupportedOperationException("OS is not supported: " + SystemUtils.OS_NAME);
        }
    }
}
