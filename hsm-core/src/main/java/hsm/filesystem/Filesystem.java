package hsm.filesystem;

import hsm.units.InformationQuantity;

/**
 * This class represents a filesystem used on data storage devices or virtual.
 *
 * @author elaskovenko
 * @since 1.0.0
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

    /**
     * Get name of filesystem.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get type of filesystem.
     *
     * @return fs type
     */
    public String getType() {
        return type;
    }

    /**
     * Get size of filesystem.
     *
     * @return fs size
     */
    public InformationQuantity getSize() {
        return size;
    }

    /**
     * Get used memory quantity.
     *
     * @return used memory
     */
    public InformationQuantity getUsed() {
        return used;
    }

    /**
     * Get available memory quantity.
     *
     * @return available memory
     */
    public InformationQuantity getAvailable() {
        return available;
    }

    /**
     * Get filesystem mount point.
     *
     * @return mount point
     */
    public String getMountPoint() {
        return mountPoint;
    }

    /**
     * Check if other object is equal to the filesystem.
     *
     * @param o object to be compared with
     * @return true if the filesystem is equal to object or false
     */
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

    /**
     * Overridden {@code Object.hashCode()} method to maintain the general contract.
     *
     * @return hash code
     */
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
     * Get string representation of the filesystem.
     *
     * @return string representation of the filesystem
     */
    @Override
    public String toString() {
        return String.format("filesystem: %s\ttype: %s\tsize: %s\tused size: %s\tavailable size: %s\tmount point: %s",
                name, type, size.toString(), used.toString(), available.toString(), mountPoint);
    }
}
