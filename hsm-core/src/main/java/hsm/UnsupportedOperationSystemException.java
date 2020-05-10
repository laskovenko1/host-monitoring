package hsm;

public class UnsupportedOperationSystemException extends RuntimeException {

    public UnsupportedOperationSystemException(OperatingSystem os) {
        super(String.format("Unsupported OS: %s", os));
    }
}
