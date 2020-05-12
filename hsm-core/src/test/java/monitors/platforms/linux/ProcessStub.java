package monitors.platforms.linux;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

class ProcessStub extends Process {

    private final String commandOutputFile;

    public ProcessStub(String commandOutputFile) {
        this.commandOutputFile = commandOutputFile;
    }

    @Override
    public InputStream getInputStream() {
        return loadCommandOutput();
    }

    private InputStream loadCommandOutput() {
        Path path = getCommandOutputPath();
        return getInputStream(path);
    }

    private Path getCommandOutputPath() {
        try {
            return Paths.get(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource(commandOutputFile)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to find test command output. Cause: ", e);
        }
    }

    private InputStream getInputStream(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load test command output. Cause: ", e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public int waitFor() {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    @Override
    public void destroy() {
    }
}
