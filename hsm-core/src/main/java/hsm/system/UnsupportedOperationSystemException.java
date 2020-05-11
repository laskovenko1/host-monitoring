package hsm.system;

public class UnsupportedOperationSystemException extends RuntimeException {

    public UnsupportedOperationSystemException(OperatingSystem os) {
        super(String.format("Unsupported operation system: %s", os));
    }
}
