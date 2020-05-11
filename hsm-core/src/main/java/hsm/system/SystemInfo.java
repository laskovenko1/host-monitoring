package hsm.system;

public class SystemInfo {

    private final String osName;

    public SystemInfo() {
        osName = getSystemPropertyValue("os.name");
    }

    private String getSystemPropertyValue(String propertyName) {
        try {
            return System.getProperty(propertyName);
        } catch (SecurityException e) {
            return null;
        }
    }

    public OperatingSystem getCurrentOS() {
        if (isLinux())
            return OperatingSystem.LINUX;
        else if (isMac())
            return OperatingSystem.MAC;
        else if (isWindows())
            return OperatingSystem.WINDOWS;
        return OperatingSystem.UNKNOWN;
    }

    private boolean isLinux() {
        return isOSNameMatch("Linux") || isOSNameMatch("LINUX");
    }

    private boolean isMac() {
        return isOSNameMatch("Mac");
    }

    private boolean isWindows() {
        return isOSNameMatch("Windows");
    }

    private boolean isOSNameMatch(String prefix) {
        if (osName == null)
            return false;
        return osName.startsWith(prefix);
    }
}
