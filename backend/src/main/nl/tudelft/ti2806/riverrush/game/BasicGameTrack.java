package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.io.InputStream;

/**
 * A basic implementation of a game track.
 */
public class BasicGameTrack extends GameTrack {

    /**
     * Create a basic level.
     *
     * @param dispatcher - See {@link EventDispatcher}
     */
    public BasicGameTrack(final EventDispatcher dispatcher, Game game) {
        super(dispatcher, game);
        InputStream in = BasicGameTrack.class.getResourceAsStream("/simpletrack.txt");
        this.parseLevel(in);

        this.addTeam(new Team());
        this.addTeam(new Team());
    }
}
