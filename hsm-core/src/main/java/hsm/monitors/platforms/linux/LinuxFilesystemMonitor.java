package hsm.monitors.platforms.linux;

import hsm.filesystem.Filesystem;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.utils.CommonUtils;
import hsm.units.BinaryPrefix;
import hsm.units.InformationQuantity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reference implementation of {@link hsm.monitors.FilesystemMonitor} for Linux.
 */
public final class LinuxFilesystemMonitor implements FilesystemMonitor {

    /**
     * Command of df command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String DF_COMMAND = "df -T";

    public static final int FILESYSTEM_COLUMN_INDEX = 0;
    public static final int TYPE_COLUMN_INDEX = 1;
    public static final int ONE_K_BLOCKS_COLUMN_INDEX = 2;
    public static final int USED_COLUMN_INDEX = 3;
    public static final int AVAILABLE_COLUMN_INDEX = 4;
    public static final int MOUNTED_ON_COLUMN_INDEX = 6;

    /**
     * Get real/virtual filesystems used on host's data storage devices limited by concrete fs types.
     *
     * @param limitedTypes filesystem types which returning list of filesystems will be limited by.
     *                     This list can be empty or {@code null} that means no limits will be applied to returning filesystems.
     * @return a list of filesystems limited by {@param limitedTypes}
     * @throws IllegalStateException if there are any errors while processing free command
     */
    @Override
    public List<Filesystem> getFilesystems(List<String> limitedTypes) {
        String dfCommand = prepareDFCommand(limitedTypes);
        Process dfProcess = CommonUtils.executeCommand(dfCommand);
        List<List<String>> fsInfoTable = parseCommandOutput(dfProcess);
        return parseFsInfoTable(fsInfoTable);
    }

    private String prepareDFCommand(List<String> limitedTypes) {
        if (limitedTypes == null || limitedTypes.isEmpty())
            return DF_COMMAND;
        return createTypeLimitedCommand(limitedTypes);
    }

    private String createTypeLimitedCommand(List<String> limitedTypes) {
        return DF_COMMAND + limitedTypes.stream()
                .map(type -> " --type=" + type)
                .reduce("", String::concat);
    }

    private List<List<String>> parseCommandOutput(Process dfProcess) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dfProcess.getInputStream()))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> line.split("\\s+"))
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            dfProcess.destroy();
        }
    }

    private List<Filesystem> parseFsInfoTable(List<List<String>> fsTable) {
        List<Filesystem> parsedFs = new ArrayList<>();
        for (List<String> row : fsTable) {
            String name = row.get(FILESYSTEM_COLUMN_INDEX);
            String type = row.get(TYPE_COLUMN_INDEX);
            String memorySize = row.get(ONE_K_BLOCKS_COLUMN_INDEX);
            String memoryUsed = row.get(USED_COLUMN_INDEX);
            String memoryAvailable = row.get(AVAILABLE_COLUMN_INDEX);
            String mountPoint = row.get(MOUNTED_ON_COLUMN_INDEX);
            Filesystem fs = new Filesystem(name, type, parseMemoryQuantity(memorySize), parseMemoryQuantity(memoryUsed),
                    parseMemoryQuantity(memoryAvailable), mountPoint);
            parsedFs.add(fs);
        }
        return parsedFs;
    }

    private InformationQuantity parseMemoryQuantity(String kBytes) {
        return new InformationQuantity(Long.parseLong(kBytes), BinaryPrefix.Ki);
    }
}
