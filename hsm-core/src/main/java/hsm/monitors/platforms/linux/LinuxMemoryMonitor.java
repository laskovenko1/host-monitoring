package hsm.monitors.platforms.linux;

import hsm.monitors.MemoryMonitor;
import hsm.monitors.memory.MemoryQuantity;
import hsm.monitors.memory.MemoryUnit;
import hsm.monitors.memory.PhysicalMemory;
import hsm.monitors.memory.VirtualMemory;
import hsm.monitors.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class LinuxMemoryMonitor implements MemoryMonitor {

    public static final int USED_COLUMN_INDEX = 2;
    public static final int AVAILABLE_COLUMN_INDEX = 3;

    @Override
    public PhysicalMemory getPhysicalMemory(MemoryUnit unit) {
        String command = getFreeCommand(unit);
        String[] memoryInfo = getMemoryInfo(CommonUtils.executeCommand(command), "Mem");
        MemoryQuantity used = new MemoryQuantity(Long.parseLong(memoryInfo[USED_COLUMN_INDEX]), unit);
        MemoryQuantity available = new MemoryQuantity(Long.parseLong(memoryInfo[AVAILABLE_COLUMN_INDEX]), unit);
        return new PhysicalMemory(used, available);
    }

    @Override
    public VirtualMemory getVirtualMemory(MemoryUnit unit) {
        String command = getFreeCommand(unit);
        String[] memoryInfo = getMemoryInfo(CommonUtils.executeCommand(command), "Swap");
        MemoryQuantity used = new MemoryQuantity(Long.parseLong(memoryInfo[USED_COLUMN_INDEX]), unit);
        MemoryQuantity available = new MemoryQuantity(Long.parseLong(memoryInfo[AVAILABLE_COLUMN_INDEX]), unit);
        return new VirtualMemory(used, available);
    }

    private String getFreeCommand(MemoryUnit unit) {
        switch (unit) {
            case B:
                return "free -b";
            case KB:
                return "free -k";
            case MB:
                return "free -m";
            case GB:
                return "free -g";
            case TB:
                return "free --tebi";
            case PB:
                return "free --pebi";
            default:
                throw new IllegalArgumentException("Unknown memory unit");
        }
    }

    private String[] getMemoryInfo(Process process, String memType) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // 1. Filter lines with (physical/virtual) memory info.
            // 2. Convert line to array.
            return reader.lines()
                    .filter(line -> line.contains(memType))
                    .map(line -> line.replaceAll(",", ".").split("\\s+"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Wrong command output"));
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            process.destroy();
        }
    }
}
