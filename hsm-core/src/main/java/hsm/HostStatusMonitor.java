package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;
import org.apache.commons.lang3.SystemUtils;

import java.util.function.Supplier;

/**
 * The main entry point to HSM.
 * <p>
 * This class provides getters for monitors which instantiate the suitable platform-specific implementations of
 * {@link hsm.monitors.CPUMonitor}, {@link hsm.monitors.MemoryMonitor} and {@link hsm.monitors.FilesystemMonitor}.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
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

    /**
     * Get current platform.
     *
     * @return current platform
     */
    public static Platform getCurrentPlatform() {
        return currentPlatform;
    }

    private final Supplier<CPUMonitor> cpuMonitor = this::createCpuMonitor;
    private final Supplier<MemoryMonitor> memoryMonitor = this::createMemoryMonitor;
    private final Supplier<FilesystemMonitor> filesystemMonitor = this::createFilesystemMonitor;

    /**
     * Get an instance of the suitable platform-specific {@link hsm.monitors.CPUMonitor}.
     *
     * @return an instance of CPU monitor
     */
    public CPUMonitor getCpuMonitor() {
        return cpuMonitor.get();
    }

    /**
     * Get an instance of the suitable platform-specific {@link hsm.monitors.MemoryMonitor}.
     *
     * @return an instance of memory monitor
     */
    public MemoryMonitor getMemoryMonitor() {
        return memoryMonitor.get();
    }

    /**
     * Get an instance of the suitable platform-specific {@link hsm.monitors.FilesystemMonitor}.
     *
     * @return an instance of filesystem monitor
     */
    public FilesystemMonitor getFilesystemMonitor() {
        return filesystemMonitor.get();
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

    private FilesystemMonitor createFilesystemMonitor() {
        switch (currentPlatform) {
            case LINUX:
                return new LinuxFilesystemMonitor();
            default:
                throw new UnsupportedOperationException("OS is not supported: " + SystemUtils.OS_NAME);
        }
    }
}
