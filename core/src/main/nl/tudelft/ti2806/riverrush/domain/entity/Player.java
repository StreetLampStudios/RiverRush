package nl.tudelft.ti2806.riverrush.domain.entity;

/**
 * Created by thomas on 19-5-15.
 */
public class Player {
    private static long highest_id = 0;
    private final long id;

    public Player() {
        this.id = highest_id + 1;
        highest_id++;
    }

    public long getId() {
        return id;
    }
}
