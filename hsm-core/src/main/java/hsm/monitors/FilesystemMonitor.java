package hsm.monitors;

import hsm.filesystem.Filesystem;

import java.util.List;

/**
 * This interface represents API for filesystem monitoring.
 */
public interface FilesystemMonitor {

    /**
     * Get real/virtual filesystems used on host's data storage devices by concrete fs types.
     *
     * @param fsTypes types of filesystems which all filesystems used on host's data storage devices will be filtered by.
     *                This list can be empty or null that means no filters will be apply to host's filesystems
     * @return a list of filtered filesystems
     */
    List<Filesystem> getFilesystems(List<String> fsTypes);
}
