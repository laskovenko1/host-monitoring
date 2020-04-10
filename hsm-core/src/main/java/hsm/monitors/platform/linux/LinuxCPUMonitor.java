package hsm.monitors.platform.linux;

import hsm.monitors.CPUMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LinuxCPUMonitor implements CPUMonitor {

    public static final String MPSTAT_COMMAND = "mpstat -P ALL 1 1";
    public static final int IDLE_COLUMN_INDEX = 11;

    @Override
    public Map<String, Double> getCpuUsage() {
        Process p = executeCommand(MPSTAT_COMMAND);
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
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            p.destroy();
        }
    }

    @Override
    public int getNumberOfCores() {
        Process p = executeCommand(MPSTAT_COMMAND);
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

    @Override
    public String toString() {
        Map<String, Double> cpuUsage = getCpuUsage();
        StringBuilder builder = new StringBuilder("CPU Monitor\nCPU\tUsage\n");
        cpuUsage.forEach((p, v) -> builder.append(String.format("%s\t%f\n", p, v)));
        return builder.toString();
    }

    private Process executeCommand(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            return runtime.exec(command);
        } catch (IOException e) {
            throw new IllegalStateException("Error while executing command: '" + command + "'. " +
                    "See the inner exception for details", e);
        }
    }
}
