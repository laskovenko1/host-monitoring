package hsm.filesystem;

import hsm.units.InformationQuantity;

public class Filesystem {

    private final String name;
    private final String type;
    private final InformationQuantity size;
    private final InformationQuantity used;
    private final InformationQuantity available;
    private final String mountPoint;

    public Filesystem(String name, String type, InformationQuantity size, InformationQuantity used, InformationQuantity available, String mountPoint) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.used = used;
        this.available = available;
        this.mountPoint = mountPoint;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public InformationQuantity getSize() {
        return size;
    }

    public InformationQuantity getUsed() {
        return used;
    }

    public InformationQuantity getAvailable() {
        return available;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Filesystem))
            return false;

        Filesystem that = (Filesystem) o;
        return name.equals(that.name) && type.equals(that.type) && size.equals(that.size) && used.equals(that.used) &&
                available.equals(that.available) && mountPoint.equals(that.mountPoint);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + used.hashCode();
        result = 31 * result + available.hashCode();
        result = 31 * result + mountPoint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("filesystem: %s\ttype: %s\tsize: %s\tused size: %s\tavailable size: %s\tmount point: %s",
                name, type, size.toString(), used.toString(), available.toString(), mountPoint);
    }
}
