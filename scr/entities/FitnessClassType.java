package entities;

public enum FitnessClassType {
    YOGA("Yoga", 15),
    BOXING("Boxing", 25),
    CARDIO("Cardio", 30);

    private final String displayName;
    private final int defaultCapacity;

    FitnessClassType(String displayName, int defaultCapacity) {
        this.displayName = displayName;
        this.defaultCapacity = defaultCapacity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultCapacity() {
        return defaultCapacity;
    }
}
