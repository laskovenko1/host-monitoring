package hsm.system;

/**
 * Defined operating systems.
 */
public enum OperatingSystem {
    LINUX(true), WINDOWS(false), MAC(false), UNKNOWN(false);

    private final boolean isSupported;

    OperatingSystem(boolean isSupported) {
        this.isSupported = isSupported;
    }

    /**
     * Get if OS has platform-specific host status monitors implementation.
     */
    public boolean isSupported() {
        return isSupported;
    }
}
