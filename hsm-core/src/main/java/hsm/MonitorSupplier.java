package hsm;

import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;

import java.util.function.Supplier;

public class MonitorSupplier {

    private Supplier<CPUMonitor> cpuMonitor;
    private Supplier<FilesystemMonitor> filesystemMonitor;
    private Supplier<MemoryMonitor> memoryMonitor;

    public MonitorSupplier() {
        MonitorFactory factory = createMonitorFactory();
        setMonitorSuppliers(factory);
    }

    private MonitorFactory createMonitorFactory() {
        OperatingSystem currentOS = OperatingSystem.getCurrentOS();
        if (currentOS == OperatingSystem.LINUX)
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
