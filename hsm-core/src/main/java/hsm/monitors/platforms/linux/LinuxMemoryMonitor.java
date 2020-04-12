package hsm.monitors.platforms.linux;

import hsm.monitors.MemoryMonitor;
import hsm.units.InformationQuantity;
import hsm.memory.PhysicalMemory;
import hsm.memory.VirtualMemory;
import hsm.monitors.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public final class LinuxMemoryMonitor implements MemoryMonitor {

    public static final String FREE_COMMAND = "free -b";
    public static final String PHYSICAL_MEMORY_ID = "Mem";
    public static final String VIRTUAL_MEMORY_ID = "Swap";
    public static final int USED_COLUMN_INDEX = 2;
    public static final int AVAILABLE_COLUMN_INDEX = 3;

    @Override
    public PhysicalMemory getPhysicalMemory() {
        String[] memoryInfo = Objects.requireNonNull(getMemoryInfo(CommonUtils.executeCommand(FREE_COMMAND), PHYSICAL_MEMORY_ID));
        InformationQuantity used = new InformationQuantity(Long.parseLong(memoryInfo[USED_COLUMN_INDEX]), null);
        InformationQuantity available = new InformationQuantity(Long.parseLong(memoryInfo[AVAILABLE_COLUMN_INDEX]), null);
        return new PhysicalMemory(used, available);
    }

    @Override
    public VirtualMemory getVirtualMemory() {
        String[] memoryInfo = getMemoryInfo(CommonUtils.executeCommand(FREE_COMMAND), VIRTUAL_MEMORY_ID);
        if (memoryInfo == null) {
            return null;
        }
        InformationQuantity used = new InformationQuantity(Long.parseLong(memoryInfo[USED_COLUMN_INDEX]), null);
        InformationQuantity available = new InformationQuantity(Long.parseLong(memoryInfo[AVAILABLE_COLUMN_INDEX]), null);
        return new VirtualMemory(used, available);
    }

    private String[] getMemoryInfo(Process process, String memoryTypeId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // 1. Filter lines with (physical/virtual) memory info.
            // 2. Convert line to array.
            return reader.lines()
                    .filter(line -> line.contains(memoryTypeId))
                    .map(line -> line.replaceAll(",", ".").split("\\s+"))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            process.destroy();
        }
    }
}
