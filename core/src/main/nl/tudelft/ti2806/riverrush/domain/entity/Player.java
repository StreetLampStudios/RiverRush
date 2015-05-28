package nl.tudelft.ti2806.riverrush.domain.entity;

/**
 * Game object representing a player.
 */
public class Player {

    /**
     * Number of bits to shift the id.
     */
    public static final int BITS = 32;
    private static long highestId = 0;
    private final long id;

    /**
     * Create the player.
     */
    public Player() {
        this.id = highestId + 1;
        highestId++;
    }

    /**
     * Create the player.
     *
     * @param uid Id of the player
     */
    public Player(final long uid) {
        this.id = uid;
        if (uid > highestId) {
            highestId = uid;
        }
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        return id == player.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> BITS));
    }
}
