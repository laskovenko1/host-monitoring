package hsm.system;

/**
 * Exception indicating that host OS is not supported.
 * See {@link OperatingSystem} for supported OS.
 */
public class UnsupportedOperationSystemException extends RuntimeException {

    public UnsupportedOperationSystemException(OperatingSystem os) {
        super(String.format("Unsupported operation system: %s", os));
    }
}
