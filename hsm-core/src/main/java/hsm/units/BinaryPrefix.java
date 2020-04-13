package hsm.units;

public enum BinaryPrefix {
    Ki(10), Mi(20), Gi(30), Ti(40), Pi(50);

    private final int power;

    BinaryPrefix(int power) {
        this.power = power;
    }

    public long getPower() {
        return power;
    }
}