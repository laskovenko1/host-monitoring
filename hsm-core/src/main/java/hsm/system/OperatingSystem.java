package hsm.system;

public enum OperatingSystem {
    LINUX(true),
    WINDOWS(false),
    MAC(false),
    UNKNOWN(false);

    private final boolean isSupported;

    OperatingSystem(boolean isSupported) {
        this.isSupported = isSupported;
    }

    public boolean isSupported() {
        return isSupported;
    }
}
