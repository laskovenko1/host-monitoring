package org.host.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CPUMonitorImpl implements CPUMonitor {

    public static final String MPSTAT_COMMAND = "mpstat -P ALL 1 1";
    public static final int IDLE_COLUMN_INDEX = 11;

    @Override
    public Map<String, Double> getCpuUsage() {
        final Map<String, Double> cpuUsageByProcessors = new HashMap<>();
        Process p = executeCommand(MPSTAT_COMMAND);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            // Skipping 3 redundant command lines:
            for (int i = 0; i < 3; ++i) {
                reader.readLine();
            }

            // Adding total CPU usage:
            String line = reader.readLine();
            String[] splittedString = line.replaceAll(",", ".").split("\\s+");
            double idleValue = Double.parseDouble(splittedString[IDLE_COLUMN_INDEX]);
            cpuUsageByProcessors.put("ALL", 100 - idleValue);

            // Adding logical processors usage:
            int processorNum = 0;
            while (!Objects.isNull(line = reader.readLine()) && line.length() > 1) {
                splittedString = line.replaceAll(",", ".").split("\\s+");
                idleValue = Double.parseDouble(splittedString[IDLE_COLUMN_INDEX]);
                cpuUsageByProcessors.put(String.valueOf(processorNum), 100 - idleValue);
                ++processorNum;
            }

            return cpuUsageByProcessors;
        } catch (IOException caught) {
            throw new CommandExecutionException(MPSTAT_COMMAND, caught);
        } finally {
            p.destroy();
        }
    }

    @Override
    public int getCpuNumber() {
        Process p = executeCommand(MPSTAT_COMMAND);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            // Skipping 4 redundant command lines:
            for (int i = 0; i < 4; ++i) {
                reader.readLine();
            }

            return reader.lines()
                    .filter(s -> s.length() > 1)
                    .map(s -> 1)
                    .reduce(0, Integer::sum);
        } catch (IOException caught) {
            throw new CommandExecutionException(MPSTAT_COMMAND, caught);
        } finally {
            p.destroy();
        }
    }

    @Override
    public String toString() {
        Map<String, Double> cpuUsage = getCpuUsage();
        StringBuilder builder = new StringBuilder("CPU Monitor\nCPU\tUsage\n");
        cpuUsage.forEach((p, v) -> builder.append(String.format("%s\t%f", p, v)));
        return builder.toString();
    }

    private Process executeCommand(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            return runtime.exec(command);
        } catch (IOException caught) {
            throw new CommandExecutionException(command, caught);
        }
    }
}
