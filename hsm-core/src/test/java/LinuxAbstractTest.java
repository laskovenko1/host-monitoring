import hsm.HostStatusMonitor;
import hsm.Platform;
import org.junit.Assume;
import org.junit.Before;

public class LinuxAbstractTest {

    protected static final HostStatusMonitor hostStatus = new HostStatusMonitor();

    @Before
    public void linuxOnly() {
        Assume.assumeTrue(HostStatusMonitor.getCurrentPlatform().equals(Platform.LINUX));
    }
}
