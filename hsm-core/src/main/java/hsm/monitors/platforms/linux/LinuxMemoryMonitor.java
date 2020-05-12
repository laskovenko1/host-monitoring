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
 */
public final class LinuxMemoryMonitor implements MemoryMonitor {

    /**
     * Command of free command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String FREE_COMMAND = "free -b";

    public static final String PHYSICAL_MEMORY_ID = "Mem";
    public static final String VIRTUAL_MEMORY_ID = "Swap";
    public static final int USED_COLUMN_INDEX = 2;
    public static final int FREE_COLUMN_INDEX = 3;
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
        Process freeProcess = CommonUtils.executeCommand(FREE_COMMAND);
        List<String> physicalMemoryInfo = Objects.requireNonNull(parseCommandOutput(freeProcess, PHYSICAL_MEMORY_ID));
        String memoryUsed = physicalMemoryInfo.get(USED_COLUMN_INDEX);
        String memoryAvailable = physicalMemoryInfo.get(AVAILABLE_COLUMN_INDEX);
        return parsePhysicalMemory(memoryUsed, memoryAvailable);
    }

    private PhysicalMemory parsePhysicalMemory(String usedBytes, String availableBytes) {
        InformationQuantity usedQuantity = parseMemoryQuantity(usedBytes);
        InformationQuantity availableQuantity = parseMemoryQuantity(availableBytes);
        return new PhysicalMemory(usedQuantity, availableQuantity);
    }

    /**
     * Get swap memory in the system.
     *
     * @return swap memory. May return null that indicates that there is no swap mem in the system.
     * @throws IllegalStateException if there are any errors while processing free command
     */
    @Override
    public VirtualMemory getVirtualMemory() {
        List<String> virtualMemoryInfo = parseCommandOutput(CommonUtils.executeCommand(FREE_COMMAND), VIRTUAL_MEMORY_ID);
        if (virtualMemoryInfo == null)
            return null;

        String memoryUsed = virtualMemoryInfo.get(USED_COLUMN_INDEX);
        String memoryFree = virtualMemoryInfo.get(FREE_COLUMN_INDEX);
        return parseVirtualMemory(memoryUsed, memoryFree);
    }

    private VirtualMemory parseVirtualMemory(String usedBytes, String availableBytes) {
        InformationQuantity usedQuantity = parseMemoryQuantity(usedBytes);
        InformationQuantity availableQuantity = parseMemoryQuantity(availableBytes);
        return new VirtualMemory(usedQuantity, availableQuantity);
    }

    private List<String> parseCommandOutput(Process process, String memoryTypeId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
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

    private InformationQuantity parseMemoryQuantity(String bytes) {
        return new InformationQuantity(Long.parseLong(bytes), null);
    }
}
