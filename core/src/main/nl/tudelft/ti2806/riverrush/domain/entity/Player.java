package nl.tudelft.ti2806.riverrush.domain.entity;

/**
 * Created by thomas on 19-5-15.
 */
public class Player {

    private static long highestId = 0;
    private final long id;

    public Player() {
        this.id = highestId + 1;
        highestId++;
    }

    public Player(final long uid) {
        this.id = uid;
        highestId = (uid > highestId) ? uid : highestId;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
