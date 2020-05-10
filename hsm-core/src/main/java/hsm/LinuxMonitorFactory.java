package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.monitors.platforms.linux.LinuxCPUMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxMemoryMonitor;

class LinuxMonitorFactory implements MonitorFactory {

    public CPUMonitor createCpuMonitor() {
        return new LinuxCPUMonitor();
    }

    public MemoryMonitor createMemoryMonitor() {
        return new LinuxMemoryMonitor();
    }

    public FilesystemMonitor createFilesystemMonitor() {
        return new LinuxFilesystemMonitor();
    }
}
