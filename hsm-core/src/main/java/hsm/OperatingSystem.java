package hsm;

import org.apache.commons.lang3.SystemUtils;

public enum OperatingSystem {
    LINUX,
    WINDOWS,
    MAC,
    UNSPECIFIED;

    public static OperatingSystem getCurrentOS() {
        if (SystemUtils.IS_OS_LINUX)
            return LINUX;
        else if (SystemUtils.IS_OS_MAC)
            return MAC;
        else if (SystemUtils.IS_OS_WINDOWS)
            return WINDOWS;
        return UNSPECIFIED;
    }
}
