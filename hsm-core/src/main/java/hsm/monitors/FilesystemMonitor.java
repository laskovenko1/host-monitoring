package hsm.monitors;

import hsm.filesystem.Filesystem;

import java.util.List;

public interface FilesystemMonitor {

    List<Filesystem> getFilesystems(List<String> types);
}
