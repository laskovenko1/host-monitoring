package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;

interface MonitorFactory {

    CPUMonitor createCpuMonitor();

    MemoryMonitor createMemoryMonitor();

    FilesystemMonitor createFilesystemMonitor();
}
