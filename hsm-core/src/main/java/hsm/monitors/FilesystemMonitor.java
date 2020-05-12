package hsm.monitors;

import hsm.filesystem.Filesystem;

import java.util.List;

/**
 * API for filesystem monitoring.
 */
public interface FilesystemMonitor {

    /**
     * Get real/virtual filesystems used on host's data storage devices limited by concrete fs types.
     *
     * @param limitedTypes filesystem types which returning list of filesystems will be limited by.
     *                     This list can be empty or {@code null} that means no limits will be applied to returning filesystems.
     * @return a list of filesystems limited by {@param limitedTypes}
     */
    List<Filesystem> getFilesystems(List<String> limitedTypes);
}
