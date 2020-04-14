package hsm.monitors.platforms.linux;

import hsm.monitors.CPUMonitor;
import hsm.monitors.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Reference implementation of {@link hsm.monitors.CPUMonitor} for Linux.
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public final class LinuxCPUMonitor implements CPUMonitor {

    /**
     * Command of mpstat command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String MPSTAT_COMMAND = "mpstat -P ALL 1 1";
    /**
     * Index of idle statistics column in mpstat report
     */
    public static final int IDLE_COLUMN_INDEX = 11;

    /**
     * Get the CPU usage statistics for logical CPU processors.
     *
     * @return usage statistic by ordinal numbers of logical CPU processors.
     * The map contains key '-1' for average usage.
     * @throws IllegalStateException if there are any errors while processing mpstat command
     */
    @Override
    public Map<String, Double> getCpuUsage() {
        Process p = CommonUtils.executeCommand(MPSTAT_COMMAND);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            // 1. Filter lines with average data.
            // 2. Skip table header.
            // 3. Map lines into usage value.
            // 4. Convert usage values into a map, where keys are numbers of logical processors (-1 for average usage value).
            AtomicInteger processorNum = new AtomicInteger(-1);
            return reader.lines()
                    .filter(line -> line.contains("Average"))
                    .skip(1)
                    .map(line -> line.replaceAll(",", ".").split("\\s+"))
                    .map(array -> 100.0d - Double.parseDouble(array[IDLE_COLUMN_INDEX]))
                    .collect(Collectors.toMap(k -> String.valueOf(processorNum.getAndIncrement()),
                            Function.identity(), (k, v) -> k, LinkedHashMap::new));
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            p.destroy();
        }
    }

    /**
     * Get the number of available logical CPU cores.
     *
     * @return the number of logical cores
     * @throws IllegalStateException if there are any errors while processing mpstat command
     */
    @Override
    public int getNumberOfCores() {
        Process p = CommonUtils.executeCommand(MPSTAT_COMMAND);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            // 1. Filter lines with average data.
            // 2. Skip table header and 'ALL' line.
            // 3. Count lines representing logical processors state.
            return reader.lines()
                    .filter(line -> line.contains("Average"))
                    .skip(2)
                    .map(line -> 1)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            p.destroy();
        }
    }

    /**
     * Get string representation of CPU monitor
     *
     * @return string representation of CPU monitor
     * @throws IllegalStateException if there are any errors while processing mpstat command
     */
    @Override
    public String toString() {
        Map<String, Double> cpuUsage = getCpuUsage();
        StringBuilder builder = new StringBuilder("CPU Monitor\nCPU\tUsage\n");
        cpuUsage.forEach((p, v) -> builder.append(String.format("%s\t%f\n", p, v)));
        return builder.toString();
    }
}
