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

public class LinuxFilesystemMonitor implements FilesystemMonitor {

    public static final String DF_COMMAND = "df -T";
    public static final int FILESYSTEM_COLUMN_INDEX = 0;
    public static final int TYPE_COLUMN_INDEX = 1;
    public static final int ONE_K_BLOCKS_COLUMN_INDEX = 2;
    public static final int USED_COLUMN_INDEX = 3;
    public static final int AVAILABLE_COLUMN_INDEX = 4;
    public static final int MOUNTED_ON_COLUMN_INDEX = 5;

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
                .reduce(String::concat);
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
