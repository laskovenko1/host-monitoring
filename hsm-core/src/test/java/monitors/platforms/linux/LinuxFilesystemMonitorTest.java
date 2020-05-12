package monitors.platforms.linux;

import hsm.MonitorProvider;
import hsm.filesystem.Filesystem;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.platforms.linux.LinuxFilesystemMonitor;
import hsm.monitors.utils.CommonUtils;
import hsm.units.BinaryPrefix;
import hsm.units.InformationQuantity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtils.class)
public class LinuxFilesystemMonitorTest {

    private static final String DF_ALL_FS_COMMAND = "df -T";
    private static final String DF_EXT_FS_COMMAND = "df -T --type=ext4 --type=ext3";
    private static final String DF_WRONG_TYPE_FS_COMMAND = "df -T --type=TEST --type=WRONG TYPE --type=!@#$R!FEDF";
    private static final String DF_ALL_FS_OUTPUT_FILE = "df_all_fs_output.out";
    private static final String DF_EXT_FS_OUTPUT_FILE = "df_ext_fs_output.out";
    private static final String DF_NO_FS_OUTPUT_FILE = "df_no_fs_output.out";
    private static final String TEST_FS_NAME = "/dev/nvme0n1p5";
    private static final String TEST_FS_MOUNT_POINT = "/";
    private static final String TEST_FS_TYPE = "ext4";
    private static final InformationQuantity TEST_FS_MEMORY_SIZE = new InformationQuantity(102684472L, BinaryPrefix.Ki);
    private static final InformationQuantity TEST_FS_MEMORY_USED = new InformationQuantity(17961772L, BinaryPrefix.Ki);
    private static final InformationQuantity TEST_FS_MEMORY_AVAILABLE = new InformationQuantity(79463440L, BinaryPrefix.Ki);
    private static final int ALL_FS_FROM_OUTPUT = 13;
    private static final int EXT_FS_FROM_OUTPUT = 2;

    private MonitorProvider monitorProvider;

    @Before
    public void setUpMocks() {
        mockMonitorProvider();
        mockCommonUtils();
    }

    private void mockMonitorProvider() {
        monitorProvider = Mockito.mock(MonitorProvider.class);
        when(monitorProvider.getFilesystemMonitor()).thenReturn(new LinuxFilesystemMonitor());
    }

    private void mockCommonUtils() {
        PowerMockito.mockStatic(CommonUtils.class);
        when(CommonUtils.executeCommand(DF_ALL_FS_COMMAND)).thenReturn(new ProcessStub(DF_ALL_FS_OUTPUT_FILE));
        when(CommonUtils.executeCommand(DF_EXT_FS_COMMAND)).thenReturn(new ProcessStub(DF_EXT_FS_OUTPUT_FILE));
        when(CommonUtils.executeCommand(DF_WRONG_TYPE_FS_COMMAND))
                .thenReturn(new ProcessStub(DF_NO_FS_OUTPUT_FILE));
    }

    @Test
    public void getAllFilesystems() {
        FilesystemMonitor filesystemMonitor = monitorProvider.getFilesystemMonitor();

        assertEquals(filesystemMonitor.getFilesystems(null), filesystemMonitor.getFilesystems(new ArrayList<>()));

        List<Filesystem> allFs = filesystemMonitor.getFilesystems(null);
        assertEquals(ALL_FS_FROM_OUTPUT, allFs.size());
        Filesystem testFs = findTestFilesystem(allFs);
        checkTestFilesystem(testFs);
    }

    private Filesystem findTestFilesystem(List<Filesystem> fsList) {
        Filesystem testFs = fsList.stream()
                .filter(f -> f.getName().equals(TEST_FS_NAME))
                .findFirst()
                .orElse(null);
        assertNotNull(testFs);
        return testFs;
    }

    private void checkTestFilesystem(Filesystem fs) {
        assertEquals(TEST_FS_NAME, fs.getName());
        assertEquals(TEST_FS_MOUNT_POINT, fs.getMountPoint());
        assertEquals(TEST_FS_TYPE, fs.getType());
        assertEquals(TEST_FS_MEMORY_SIZE, fs.getSize());
        assertEquals(TEST_FS_MEMORY_USED, fs.getUsed());
        assertEquals(TEST_FS_MEMORY_AVAILABLE, fs.getAvailable());
    }

    @Test
    public void getFilteredFilesystems() {
        FilesystemMonitor filesystemMonitor = monitorProvider.getFilesystemMonitor();

        List<Filesystem> extFs = filesystemMonitor.getFilesystems(Arrays.asList("ext4", "ext3"));
        assertEquals(EXT_FS_FROM_OUTPUT, extFs.size());
    }

    @Test
    public void getWrongTypeFilesystems() {
        FilesystemMonitor filesystemMonitor = monitorProvider.getFilesystemMonitor();

        List<Filesystem> wrongType = filesystemMonitor.getFilesystems(Arrays.asList("TEST", "WRONG TYPE", "!@#$R!FEDF"));
        assertTrue(wrongType.isEmpty());
    }
}
