package hsm.client;

import hsm.HostStatusMonitor;
import hsm.filesystem.Filesystem;
import hsm.monitors.CPUMonitor;
import hsm.monitors.FilesystemMonitor;
import hsm.monitors.MemoryMonitor;
import org.apache.commons.cli.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Application {

    public static long DEFAULT_DELAY = 1000;

    private static final Options options = new Options();

    static {
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Show help.")
                .build());
        options.addOption(Option.builder("d")
                .longOpt("delay")
                .desc("Specifies the delay in SECONDS between screen updates.")
                .hasArg()
                .argName("SECONDS")
                .type(Integer.TYPE)
                .build());
        options.addOption(Option.builder("C")
                .longOpt("cpu")
                .desc("Enable CPU monitoring.")
                .build());
        options.addOption(Option.builder("M")
                .longOpt("mem")
                .desc("Enable memory monitoring. VIRTUAL is an boolean arg used to enable virtual memory monitoring " +
                        "(not monitored if VIRTUAL is omitted).")
                .hasArg()
                .optionalArg(true)
                .argName("VIRTUAL")
                .type(Boolean.TYPE)
                .build());
        options.addOption(Option.builder("F")
                .longOpt("fs")
                .desc("Enable filesystem monitoring. TYPE_LIST argument used to limit listing to filesystems of types " +
                        "in comma-separated TYPE_LIST. List all types if TYPE_LIST is omitted.")
                .hasArgs()
                .optionalArg(true)
                .valueSeparator(',')
                .argName("TYPE_LIST")
                .type(String.class)
                .build());
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                showHelp();
                System.exit(0);
            }
            if (!line.hasOption('C') && !line.hasOption('M') && !line.hasOption('F')) {
                System.out.println("Nothing to monitor.");
                System.exit(0);
            }

            long delayInMillis = DEFAULT_DELAY;
            if (line.hasOption('d')) {
                try {
                    delayInMillis = Integer.parseInt(line.getOptionValue('d')) * 1000;
                    if (delayInMillis < 0) {
                        throw new NumberFormatException("Seconds arg can't be less than 0");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Wrong format of delay option. " + e.getMessage());
                    System.exit(1);
                }
            }

            boolean monitorVirtual = false;
            String virtualArg = line.getOptionValue('M');
            if (virtualArg != null) {
                if (!virtualArg.equalsIgnoreCase("true") && !virtualArg.equalsIgnoreCase("false")) {
                    System.err.println("Wrong format of -M argument. Should be 'true' or 'false'.");
                    System.exit(1);
                }
                monitorVirtual = Boolean.parseBoolean(virtualArg);
            }

            List<String> fsTypes = new ArrayList<>();
            String[] fsTypeArgs = line.getOptionValues('F');
            if (fsTypeArgs != null) {
                fsTypes.addAll(Arrays.asList(fsTypeArgs));
            }

            HostStatusMonitor hsMonitor = new HostStatusMonitor();
            while (true) {
                try {
                    monitor(hsMonitor, line.hasOption('C'), line.hasOption('M'), monitorVirtual, line.hasOption('F'), fsTypes);
                    Thread.sleep(delayInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            System.err.println("Command line parsing failed. Reason: " + e.getMessage());
        }
    }

    private static void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar hsm-client.jar",
                "\nDESCRIPTION:\nHSM-client - a host's OS information library (HSM) client " +
                        "that allows to retrieve host's system information such as CPU, memory and filesystem info.\n\nOPTIONS:",
                options, "\nEXAMPLE:\njava -jar hsm-client.jar -d 2 -C -M true -F ext4,ext3,fat32", true);
    }

    private static void monitor(HostStatusMonitor hsMonitor,
                                  boolean cpu,
                                  boolean memory, boolean virtual,
                                  boolean filesystem, List<String> fsTypes) {
        LocalDateTime curDate = LocalDateTime.now();
        StringBuilder builder = new StringBuilder(String.format("Host status monitoring: %s\n",curDate.toString()));
        if (cpu)
            builder.append(hsMonitor.getCpuMonitor()).append('\n');
        if (memory) {
            MemoryMonitor memoryMonitor = hsMonitor.getMemoryMonitor();
            builder.append("Memory monitor:\n");
            builder.append(memoryMonitor.getPhysicalMemory());
            if (virtual)
                builder.append(memoryMonitor.getVirtualMemory());
            builder.append('\n');
        }
        if (filesystem) {
            FilesystemMonitor filesystemMonitor = hsMonitor.getFilesystemMonitor();
            List<Filesystem> filesystems = filesystemMonitor.getFilesystems(fsTypes);
            builder.append("Filesystem monitor:\n");
            builder.append(String.format("%-15s\t%-10s\t%-15s\t%-15s\t%-15s\t%s\n",
                    "Name", "Type", "Size", "Used", "Available", "Mounted on"));
            filesystems.forEach(builder::append);
        }
        System.out.println(builder);
    }
}
