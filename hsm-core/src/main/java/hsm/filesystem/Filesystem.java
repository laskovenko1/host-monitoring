package hsm.filesystem;

import hsm.units.InformationQuantity;

/**
 * Represents a filesystem used on data storage devices or virtual.
 */
public class Filesystem {

    private final String name;
    private final String type;
    private final InformationQuantity size;
    private final InformationQuantity used;
    private final InformationQuantity available;
    private final String mountPoint;

    /**
     * Create a filesystem instance.
     *
     * @param name       name of filesystem
     * @param type       type of filesystem (for example, ext4, ntfs and so on)
     * @param size       size of filesystem
     * @param used       used quantity of memory
     * @param available  available quantity of memory (size - used)
     * @param mountPoint point where the fs is mounted on
     */
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

    /**
     * @return string representation of the filesystem.
     * Result string: "name type size used_size available_size mount_point".
     */
    @Override
    public String toString() {
        return String.format("%-15s\t%-10s\t%-15s\t%-15s\t%-15s\t%s\n",
                name, type, size.toString(), used.toString(), available.toString(), mountPoint);
    }
}
