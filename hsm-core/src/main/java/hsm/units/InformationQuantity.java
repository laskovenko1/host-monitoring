package hsm.units;

import java.text.DecimalFormat;

public class InformationQuantity {

    private long bytes;
    private BinaryPrefix prefix;

    public InformationQuantity(long bytes, BinaryPrefix prefix) {
        this.bytes = bytes;
        this.prefix = prefix;
    }

    public long getBytes() {
        return bytes;
    }

    public BinaryPrefix getPrefix() {
        return prefix;
    }

    public void setPrefix(BinaryPrefix prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        if (prefix == null) {
            return String.format("%dB", bytes);
        }
        return String.format("%d%s", bytes >> prefix.getPower(), prefix.toString());
    }
}
