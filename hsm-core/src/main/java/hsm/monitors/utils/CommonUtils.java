package hsm.monitors.utils;

import java.io.IOException;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static Process executeCommand(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            return runtime.exec(command);
        } catch (IOException e) {
            throw new IllegalStateException("Error while executing command: '" + command + "'. " +
                    "See the inner exception for details", e);
        }
    }
}
