package hsm.monitors.platforms.linux;

import hsm.cpu.CPUCore;
import hsm.cpu.CentralProcessingUnit;
import hsm.filesystem.Filesystem;
import hsm.monitors.CPUMonitor;
import hsm.monitors.utils.CommonUtils;
import hsm.units.Percent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Reference implementation of {@link hsm.monitors.CPUMonitor} for Linux.
 */
public final class LinuxCPUMonitor implements CPUMonitor {

    /**
     * Command of mpstat command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String MPSTAT_COMMAND = "mpstat -P ALL 1 1";

    public static final int CPU_COLUMN_INDEX = 1;
    public static final int IDLE_COLUMN_INDEX = 11;

    /**
     * Get {@link CentralProcessingUnit} object.
     *
     * @throws IllegalStateException if there are any errors while processing mpstat command
     */
    @Override
    public CentralProcessingUnit getCPU() {
        Process mpstatProcess = CommonUtils.executeCommand(MPSTAT_COMMAND);
        List<List<String>> cpuInfoTable = parseCommandOutput(mpstatProcess);
        List<CPUCore> cores = parseCpuInfoTable(cpuInfoTable);
        return new CentralProcessingUnit(cores);
    }

    private List<List<String>> parseCommandOutput(Process mpstatProcess) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mpstatProcess.getInputStream()))) {
            return reader.lines()
                    .filter(line -> line.contains("Average"))
                    .skip(2) // Skipping table header and average CPU statistic line in mpstat report
                    .map(line -> line.replaceAll(",", ".").split("\\s+"))
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            mpstatProcess.destroy();
        }
    }

    private List<CPUCore> parseCpuInfoTable(List<List<String>> cpuTable) {
        List<CPUCore> cores = new ArrayList<>();
        for (List<String> row : cpuTable) {
            String number = row.get(CPU_COLUMN_INDEX);
            String idle = row.get(IDLE_COLUMN_INDEX);
            CPUCore core = new CPUCore(Integer.parseInt(number), parseUsagePercent(idle));
            cores.add(core);
        }
        return cores;
    }

    private Percent parseUsagePercent(String idle) {
        return new Percent(100.0d - Double.parseDouble(idle));
    }
}
