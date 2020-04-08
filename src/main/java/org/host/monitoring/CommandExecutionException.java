package org.host.monitoring;

public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException(String command, Throwable cause) {
        super(String.format("Error while executing command: \'%s\'. See the inner exception for details.", command), cause);
    }
}
