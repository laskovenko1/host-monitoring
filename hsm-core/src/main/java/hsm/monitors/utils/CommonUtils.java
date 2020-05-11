package hsm.monitors.utils;

import java.io.IOException;

/**
 * Static common utility methods.
 */
public final class CommonUtils {

    private CommonUtils() {
    }

    /**
     * Execute a string command via {@link java.lang.Runtime}.
     *
     * @param command specified string command
     * @return an instance of {@link Process} of the subprocess
     * @throws IllegalStateException if any error occurs while executing the command
     */
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
