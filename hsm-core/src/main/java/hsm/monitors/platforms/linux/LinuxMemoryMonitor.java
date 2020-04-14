package hsm.monitors.platforms.linux;

import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.utils.CommonUtils;
import hsm.units.InformationQuantity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Reference implementation of {@link hsm.monitors.MemoryMonitor} for Linux.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public final class LinuxMemoryMonitor implements MemoryMonitor {

    /**
     * Command of free command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String FREE_COMMAND = "free -b";
    /**
     * Index of physical memory id column in free report
     */
    public static final String PHYSICAL_MEMORY_ID = "Mem";
    /**
     * Index of virtual memory id column in free report
     */
    public static final String VIRTUAL_MEMORY_ID = "Swap";
    /**
     * Index of used memory amount column in free report
     */
    public static final int USED_COLUMN_INDEX = 2;
    /**
     * Index of free memory amount column in free report
     */
    public static final int FREE_COLUMN_INDEX = 3;
    /**
     * Index of available memory amount column in free report
     */
    public static final int AVAILABLE_COLUMN_INDEX = 6;

    /**
     * Get physical memory available in the system.
     *
     * @return physical memory
     * @throws IllegalStateException if there are any errors while processing free command
     * @throws NullPointerException  can't return null due to physical memory always exists in system
     */
    @Override
    public PhysicalMemory getPhysicalMemory() {
        List<String> memoryInfo = Objects.requireNonNull(getMemoryInfo(CommonUtils.executeCommand(FREE_COMMAND), PHYSICAL_MEMORY_ID));
        InformationQuantity used = new InformationQuantity(Long.parseLong(memoryInfo.get(USED_COLUMN_INDEX)), null);
        InformationQuantity available = new InformationQuantity(Long.parseLong(memoryInfo.get(AVAILABLE_COLUMN_INDEX)), null);
        return new PhysicalMemory(used, available);
    }

    /**
     * Get swap memory in the system.
     *
     * @return swap memory. May return null that indicates that there is no swap mem in the system.
     * @throws IllegalStateException if there are any errors while processing free command
     */
    @Override
    public VirtualMemory getVirtualMemory() {
        List<String> memoryInfo = getMemoryInfo(CommonUtils.executeCommand(FREE_COMMAND), VIRTUAL_MEMORY_ID);
        if (memoryInfo == null)
            return null;

        InformationQuantity used = new InformationQuantity(Long.parseLong(memoryInfo.get(USED_COLUMN_INDEX)), null);
        InformationQuantity free = new InformationQuantity(Long.parseLong(memoryInfo.get(FREE_COLUMN_INDEX)), null);
        return new VirtualMemory(used, free);
    }

    private List<String> getMemoryInfo(Process process, String memoryTypeId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // 1. Filter lines with (physical/virtual) memory info.
            // 2. Split line.
            // 3. Collect to list.
            return reader.lines()
                    .filter(line -> line.contains(memoryTypeId))
                    .map(line -> line.split("\\s+"))
                    .map(Arrays::asList)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            process.destroy();
        }
    }
}
