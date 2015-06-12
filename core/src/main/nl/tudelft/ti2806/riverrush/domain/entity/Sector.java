package nl.tudelft.ti2806.riverrush.domain.entity;

/**
 * Represents the sectors available on any boat.
 * Sectors are rough location indicators.
 */
public enum Sector {

    /**
     * The different sections of the boat.
     */
    FRONT(0), FRONT_MIDDLE(1), MIDDLE(2), BACK_MIDDLE(3), BACK(4);

    private final int value;

    /**
     * Create a sector.
     *
     * @param sectorIndex The id of the sector
     */
    Sector(final int sectorIndex) {
        this.value = sectorIndex;
    }

    /**
     * Get the index of this sector for array access purposes.
     * @return The integer index.
     */
    public int getIndex() {
        return value;
    }

    /**
     * Count the amount of available sectors.
     * @return The amount of sectors on any boat.
     */
    public static int countSectors() {
        return Sector.values().length;
    }

    /**
     * Returns the next sector in the logical ordering.
     * @return The sector that comes after the current one.
     */
    public Sector getNext() {
        return Sector.values()[(this.getIndex() + 1) % countSectors()];
    }
}
