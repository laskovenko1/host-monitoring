package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import hsm.system.OperatingSystem;
import hsm.system.SystemInfo;
import hsm.system.UnsupportedOperationSystemException;

import java.util.function.Supplier;

import static hsm.system.OperatingSystem.LINUX;

public class MonitorProvider {

    private Supplier<CPUMonitor> cpuMonitor;
    private Supplier<FilesystemMonitor> filesystemMonitor;
    private Supplier<MemoryMonitor> memoryMonitor;

    public MonitorProvider() {
        MonitorFactory factory = createPlatformSpecificFactory();
        setMonitorSuppliers(factory);
    }

    private MonitorFactory createPlatformSpecificFactory() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem currentOS = systemInfo.getCurrentOS();
        if (currentOS == LINUX)
            return new LinuxMonitorFactory();
        else
            throw new UnsupportedOperationSystemException(currentOS);
    }

    private void setMonitorSuppliers(MonitorFactory factory) {
        cpuMonitor = factory::createCpuMonitor;
        filesystemMonitor = factory::createFilesystemMonitor;
        memoryMonitor = factory::createMemoryMonitor;
    }

    public CPUMonitor getCpuMonitor() {
        return cpuMonitor.get();
    }

    public FilesystemMonitor getFilesystemMonitor() {
        return filesystemMonitor.get();
    }

    public MemoryMonitor getMemoryMonitor() {
        return memoryMonitor.get();
    }
}
