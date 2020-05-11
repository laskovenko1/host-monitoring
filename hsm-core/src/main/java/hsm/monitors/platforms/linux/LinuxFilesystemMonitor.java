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
 *
 * @author elaskovenko
 * @since 1.0.0
 */
public final class LinuxFilesystemMonitor implements FilesystemMonitor {

    /**
     * Command of df command-line software in UNIX-type systems to be executed by JVM
     */
    public static final String DF_COMMAND = "df -T";
    /**
     * Index of fs name column in df report
     */
    public static final int FILESYSTEM_COLUMN_INDEX = 0;
    /**
     * Index of fs type column in df report
     */
    public static final int TYPE_COLUMN_INDEX = 1;
    /**
     * Index of fs 1K-blocks (size) column in df report
     */
    public static final int ONE_K_BLOCKS_COLUMN_INDEX = 2;
    /**
     * Index of fs used size column in df report
     */
    public static final int USED_COLUMN_INDEX = 3;
    /**
     * Index of fs available size column in df report
     */
    public static final int AVAILABLE_COLUMN_INDEX = 4;
    /**
     * Index of fs mount point column in df report
     */
    public static final int MOUNTED_ON_COLUMN_INDEX = 6;

    /**
     * Get real/virtual filesystems used in system by concrete fs types.
     *
     * @param fsTypes types of filesystems which all filesystems used in system will be filtered by.
     *                Empty or null list means no filters will be apply to host's filesystems
     * @return a list of filtered filesystems
     * @throws IllegalStateException if there are any errors while processing free command
     */
    @Override
    public List<Filesystem> getFilesystems(List<String> fsTypes) {
        Process p = CommonUtils.executeCommand(prepareCommand(fsTypes));
        List<List<String>> fsInfoList = getFilesystemsInfo(p);
        List<Filesystem> filesystems = new ArrayList<>();
        for (List<String> fsInfo : fsInfoList) {
            filesystems.add(new Filesystem(fsInfo.get(FILESYSTEM_COLUMN_INDEX),
                    fsInfo.get(TYPE_COLUMN_INDEX),
                    new InformationQuantity(Long.parseLong(fsInfo.get(ONE_K_BLOCKS_COLUMN_INDEX)), BinaryPrefix.Ki),
                    new InformationQuantity(Long.parseLong(fsInfo.get(USED_COLUMN_INDEX)), BinaryPrefix.Ki),
                    new InformationQuantity(Long.parseLong(fsInfo.get(AVAILABLE_COLUMN_INDEX)), BinaryPrefix.Ki),
                    fsInfo.get(MOUNTED_ON_COLUMN_INDEX)));
        }
        return filesystems;
    }

    private String prepareCommand(List<String> fsTypes) {
        if (fsTypes == null || fsTypes.isEmpty()) {
            return DF_COMMAND;
        }
        return DF_COMMAND + fsTypes.stream()
                .map(type -> " --type=" + type)
                .reduce("", String::concat);
    }

    private List<List<String>> getFilesystemsInfo(Process process) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> line.split("\\s+"))
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading command output. See the inner exception for details", e);
        } finally {
            process.destroy();
        }
    }
}
